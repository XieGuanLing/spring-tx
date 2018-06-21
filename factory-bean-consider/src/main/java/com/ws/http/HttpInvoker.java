package com.ws.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by gl
 * on 2018/5/2
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpInvoker {

    enum HttpMethod{
        GET, POST
    }

    HttpMethod method() default HttpMethod.POST;

    String schema() default "";

    String host() default "";

    int port() default 80;

    String path() default "";

    int timeout() default 5000;

}
