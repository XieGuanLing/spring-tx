package com.ws;

import com.ws.config.AppConfig;
import com.ws.lock.LockTestService;
import com.ws.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created by gl on 2017/9/18.
 */
public class Application {

    public static void main(String[] args) throws Exception{
//        ConfigurableApplicationContext context = new SpringApplicationBuilder()
//                .bannerMode(Banner.Mode.CONSOLE)
//                .sources(AppConfig.class)
//                .run();


       // SpringApplication.run(Application.class, args);


        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        等价
//        AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext();
//        context.scan("com.ws");
//        context.refresh();
//        context.getBean("userServiceImplV1");

        UserService userService = context.getBean("userServiceImplV1", UserService.class);
        userService.say();
        userService.save(null);

//        收到调用或者UserService 加@Validated 注解，由 MethodValidationPostProcessor处理，抛出异常
//        // 2.获取到目标类的实例
//        // 3.获取目标类需要验证的方法
//        Method method = userService.getClass().getMethod("save", UserEntry.class);
//        // 4.获取目标方法的入参
////        Object[] parameterValues = { null };
//        Object[] parameterValues = { new UserEntry() };
//        // 5.获取bean validation验证类(单例即可)
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//        // 6.验证目标方法的入参
//        Set<ConstraintViolation<UserService>> set = validator.forExecutables().validateParameters(userService, method, parameterValues, new Class<?>[0]);
//        // 7.输出验证结果
//        for (ConstraintViolation<UserService> constraintViolation : set) {
//            System.out.println(constraintViolation);
//        }


        LockTestService lockTestService = context.getBean(LockTestService.class);
        lockTestService.update("98765432");

    }
}
