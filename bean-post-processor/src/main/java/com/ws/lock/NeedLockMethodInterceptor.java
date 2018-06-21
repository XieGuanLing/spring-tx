package com.ws.lock;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * create by gl
 * on 2018/6/20
 */
@Component
public class NeedLockMethodInterceptor implements MethodInterceptor {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();

        NeedLock needLock = method.getAnnotation(NeedLock.class);
        if (null == needLock) {
            return invocation.proceed();
        }

        //前缀
        String prefix =   needLock.value();
        String key = buildRedisKey(prefix, method, args);

        // 执行方法前
        lock(key);

        Object result = null;
        try {
            // 通过反射机制调用目标方法
            result = invocation.proceed();
        } catch (Exception e) {

        } finally {
            // 执行方法后解锁
            unLock(key);
        }
        return result;
    }

    private void lock(String key){
        RLock lock = redissonClient.getLock(key);
        lock.lock();
    }

    private void unLock(String key){
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    private String buildRedisKey(String key, Method method, Object[] args) throws NoSuchFieldException, IllegalAccessException {

        // 迭代全部参数的注解，根据使用KeyParam的注解的参数所在的下标，来获取args中对应下标的参数值拼接到前半部分key上  
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {

            // 循环该参数全部注解  
            for (Annotation annotation : parameterAnnotations[i]) {

                // 当前参数的注解不包含keyParam  
                if(!annotation.annotationType().isInstance(KeyParam.class)){
                    continue;
                }

                // 当前参数的注解包含keyParam,获取注解配置的值  
                String[] columns = ((KeyParam)annotation).columns();
                if (columns.length == 0) {

                    // 普通数据类型直接拼接  
                    if (null == args[i]) {
                        throw new RuntimeException("动态参数不能为null！");
                    }
                    key += args[i];
                }else{

                    // keyParam的columns值不为null，所以当前参数应该是对象类型  
                    for (int j = 0; j < columns.length; j++) {
                        Class<? extends Object> clazz = args[i].getClass();
                        Field declaredField = clazz.getDeclaredField(columns[j]);
                        declaredField.setAccessible(true);
                        Object value = declaredField.get(clazz);
                        key += value;
                    }
                }

            }
        }
        return key;
    }

}
