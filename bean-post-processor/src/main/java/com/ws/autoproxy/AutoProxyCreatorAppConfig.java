package com.ws.autoproxy;

import com.ws.lock.NeedLockAspect;
import com.ws.lock.NeedLockMethodInterceptor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by gl
 * on 2018/6/21
 *
 * 三种自动代理创建器的
 * BeanNameAutoProxyCreator 它是根据拦截器和设置的Bean的名称表达式做匹配来创建代理
 * DefaultAdvisorAutoProxyCreator
 * AbstractAdvisorAutoProxyCreator
 *
 */
@Configuration
public class AutoProxyCreatorAppConfig {

    private Logger logger = LoggerFactory.getLogger(NeedLockAspect.class);


    /**
     * 创建Advice
     */
    @Bean
    public Advice methodInterceptor(){
        return new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                logger.debug("================== >> {} 调用前", invocation.getMethod().getName());
                return                 invocation.proceed();
            }
        };
    }

    /**
     * 使用BeanNameAutoProxyCreator来创建代理，没起作用
     *
     * @return
     */
//    @Bean
//    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(){
//        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
//        //设置要创建代理的那些Bean的名字
//        beanNameAutoProxyCreator.setBeanNames("sa*");
//        //设置拦截链名字(这些拦截器是有先后顺序的)
//        beanNameAutoProxyCreator.setInterceptorNames("methodInterceptor");
//        return beanNameAutoProxyCreator;
//    }


    /**
     * 使用Advice创建Advisor, 搭配DefaultAdvisorAutoProxyCreator起作用
     */
    @Bean
    public NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor(){
        NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
        nameMatchMethodPointcutAdvisor.setMappedName("sa*");
        nameMatchMethodPointcutAdvisor.setAdvice(methodInterceptor());
        return nameMatchMethodPointcutAdvisor;
    }


    @Bean
    public NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor2(NeedLockMethodInterceptor methodInterceptor){
        NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor = new NameMatchMethodPointcutAdvisor();
        nameMatchMethodPointcutAdvisor.setMappedName("up*");
        nameMatchMethodPointcutAdvisor.setAdvice(methodInterceptor);
        return nameMatchMethodPointcutAdvisor;
    }

    /**
     *A more general and extremely powerful auto proxy creator is DefaultAdvisorAutoProxyCreator.
     * This will automagically apply eligible advisors in the current context, without the need
     * to include specific bean names in the autoproxy advisor's bean definition. It offers the
     * same merit of consistent configuration and avoidance of duplication as BeanNameAutoProxyCreator.
     *
     * Using this mechanism involves:
     *
     * 1. Specifying a DefaultAdvisorAutoProxyCreator bean definition.
     *
     * 2. Specifying any number of Advisors in the same or related contexts. Note that these must be Advisors,
     * not just interceptors or other advices. This is necessary because there must be a pointcut to evaluate,
     * to check the eligibility of each advice to candidate bean definitions.
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }
}
