package com.ws.http;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * create by gl
 * on 2018/5/2
 */
public class HttpFactoryBean implements FactoryBean {

    private Object object;

    private Class objectType;


    public HttpFactoryBean(Class objectType){
        this.objectType = objectType;
        this.object = Proxy.newProxyInstance(
                objectType.getClassLoader(),
                new Class[]{objectType},
                new HttpInvocationHandler()
        );
    }

    @Override
    public Object getObject() throws Exception {
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
