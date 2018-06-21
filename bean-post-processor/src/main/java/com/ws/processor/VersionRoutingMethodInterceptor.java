package com.ws.processor;

import com.ws.annotation.RoutingSwitch;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * create by gl
 * on 2018/6/21
 */
public class VersionRoutingMethodInterceptor implements MethodInterceptor {

    private Map<String, Object> candidates;
    private String simpleName;

    public VersionRoutingMethodInterceptor(Class targetClass, Map<String, Object> candidates){
        //注意如果接口写成IUserService,  则simpleName为iUserService, 和实现类的默认Bean名称不一致
        this.simpleName = toLowerCaseFirstOne(targetClass.getSimpleName());
        this.candidates = candidates;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if(method.isAnnotationPresent(RoutingSwitch.class)){
            //switchName 为 userServiceImplV1, userServiceImplV2
            String switchName = method.getAnnotation(RoutingSwitch.class).value();
            //调用对应版本号实例的方法
            invocation.getMethod().invoke(getTargetBean(switchName), invocation.getArguments());
        }
        return null;
    }

    /**
     * 根据版本号匹配合适的注入对象
     *
     * @param switchName
     * @return
     */
    private Object getTargetBean(String switchName){
        return candidates.get(simpleName+switchName);
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder())
                    .append(Character.toLowerCase(s.charAt(0)))
                    .append(s.substring(1))
                    .toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
