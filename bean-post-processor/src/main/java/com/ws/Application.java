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

    public static void main(String[] args){
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

        LockTestService lockTestService = context.getBean(LockTestService.class);
        lockTestService.update("98765432");

    }
}
