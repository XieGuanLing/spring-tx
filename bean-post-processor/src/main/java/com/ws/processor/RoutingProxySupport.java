package com.ws.processor;

import com.ws.annotation.RoutingSwitch;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by gl on 2017/9/16.
 */
public class RoutingProxySupport {

    public static Object createProxy(Class targetClass, Map<String, Object> candidates){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(targetClass);
        proxyFactory.addAdvice(new VersionRoutingMethodInterceptor(targetClass, candidates));
        return proxyFactory.getProxy();
    }

}
