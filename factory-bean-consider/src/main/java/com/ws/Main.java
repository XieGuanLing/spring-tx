package com.ws;

import com.ws.http.LocalHttpRequest;
import com.ws.http.dto.QueryUserCommand;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(context.getBean("dateFactoryBean", Date.class));
        System.out.println(context.getBean("stringFactoryBean", String.class));

        System.out.println(context.getBean("&dateFactoryBean"));
        System.out.println(context.getBean("&stringFactoryBean"));

        LocalHttpRequest localHttpRequest = context.getBean("localHttpClient", LocalHttpRequest.class);
        System.out.println(localHttpRequest.queryUsers(new QueryUserCommand(0, 10)));
    }

}
