package com.ws.processor;

import org.springframework.aop.framework.ProxyFactory;

import java.util.Map;

/**
 * Created by gl on 2017/9/16.
 */
public class RoutingProxySupport {

    /**
     * ProxyFactory是动态代理的核心，
     * @see org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#createProxy
     * @param targetClass
     * @param candidates
     * @return
     */
    public static Object createProxy(Class targetClass, Map<String, Object> candidates){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(targetClass);
        proxyFactory.addAdvice(new VersionRoutingMethodInterceptor(targetClass, candidates));
        return proxyFactory.getProxy();
    }

}
