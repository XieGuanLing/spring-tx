package com.ws.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by gl on 2017/9/16.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Component
public @interface RoutingInject {

}
