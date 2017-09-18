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
