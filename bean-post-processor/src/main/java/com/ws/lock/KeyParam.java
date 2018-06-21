package com.ws.lock;

import java.lang.annotation.*;

/**
 * create by gl
 * on 2018/6/20
 */
@Documented
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyParam {

    /**
     * 如果动态参数在command对象中,那么就需要设置columns的值为command对象中的属性名可以为多个,否则不需要设置该值
     * <p>例1：public void test(@KeyParam({"id"}) MemberCommand member)
     * <p>例2：public void test(@KeyParam({"id","loginName"}) MemberCommand member)
     * <p>例3：public void test(@KeyParam String memberId)
     * @return
     */
    String[] columns() default {};
}