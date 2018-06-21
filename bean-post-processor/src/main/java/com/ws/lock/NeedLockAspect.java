package com.ws.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by gl
 * on 2018/6/20
 */
@Component
@Aspect
public class NeedLockAspect {

    private Logger logger = LoggerFactory.getLogger(NeedLockAspect.class);

//    @Pointcut("execution(public * com.ws.lock+.*(..))")
//    @Pointcut(value = "@annotation(com.ws.lock.NeedLock)")
//    private void lockMethod() {
//
//    }

    @Around("@annotation(com.ws.lock.NeedLock)")
    public Object repositoryQueryMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("kind : {}, args: {}" , joinPoint.getKind(), joinPoint.getArgs());
        return joinPoint.proceed();
    }


}
