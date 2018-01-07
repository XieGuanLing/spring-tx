package com.ws.processor;

import com.ws.annotation.RoutingInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by gl on 2017/9/16.
 https://mp.weixin.qq.com/s?__biz=MjM5MDI3MjA5MQ==&mid=2697266477&idx=2&sn=984b552b462cd38ecd7b05339a1004c5&chksm=8376fa19b401730f2c03a5ad0abc43845d6009f6583bf2bf659b0a9c24654e46d577d98e5e56&mpshare=1&scene=1&srcid=0914lqRjXy87oVLbkszjOIFF&pass_ticket=22SDd1eK9RATrSWrHqeHxdv91so1882K8IvkboRoHiM=#rd

 Spring容器通过BeanPostProcessor给了我们一个机会对Spring管理的bean进行再加工。
 比如：我们可以修改bean的属性，可以给bean生成一个动态代理实例等等。

 背景：
 我们项目中经常会涉及AB 测试，这就会遇到同一套接口会存在两种不同实现。
 实验版本与对照版本需要在运行时同时存在。 现在要达到动态切换，减少if-else冗余代码

 首先自定义了两个注解：
 RoutingInjected、RoutingSwitch，前者的作用类似于我们常用的Autowired，
 声明了该注解的属性将会被注入一个路由代理类实例；后者的作用则是一个配置开关，声明了控制路由的开关属性

 在RoutingBeanPostProcessor类中，我们在postProcessAfterInitialization方法中通过检查bean中是否
 存在声明了RoutingInjected注解的属性，如果发现存在该注解则给该属性注入一个动态代理类实例

 RoutingBeanProxyFactory类功能就是生成一个代理类实例，代理类的逻辑也比较简单。版本路由支持到方法级别，
 即优先检查方法是否存在路由配置RoutingSwitch，方法不存在配置时才默认使用类路由配置

 */

@Configuration
@Component
public class RoutingBeanPostProcessor implements BeanPostProcessor{

    Logger logger = LoggerFactory.getLogger(RoutingBeanPostProcessor.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        //getFields() VS getDeclaredFields()
        logger.debug("postProcessAfterInitialization: benName[{}]" + beanName, Arrays.toString(clazz.getDeclaredFields()));
        Arrays.asList(clazz.getDeclaredFields()).forEach(field -> {
            if(field.isAnnotationPresent(RoutingInject.class)){
                if(field.getType().isInterface()){
                    handleRoutingInject(bean, field);
                }else{
                    throw new BeanCreationException(field.getName() + " not interface");
                }
            }
        });
        return bean;
    }


    private void handleRoutingInject(Object bean, Field field){
        //Map<String, Object> candidates = applicationContext.getBeansOfType(field.getType());
        Class type = field.getType();
        Map<String, Object> candidates = applicationContext.getBeansOfType(type);
        field.setAccessible(true);
        try {
            if(candidates.size()==1){
                field.set(bean, candidates.values().iterator().next());
            }else if(candidates.size()==2){
                Object proxy = RoutingProxy.createProxy(type, candidates);
                if(proxy==null){
                    throw new BeanCreationException(field.getName() + " not suitable candidate");
                }
                field.set(bean,  proxy);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
