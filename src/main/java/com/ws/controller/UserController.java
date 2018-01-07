package com.ws.controller;

import com.ws.annotation.RoutingInject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ws.service.UserService;

/**
 * Created by gl on 2017/9/18.
 */
@Controller
public class UserController {


    /**
     * 某个方法需要动态切换
     */
    @RoutingInject
    private UserService userService;


    @RequestMapping("/")
    @ResponseBody
    public String home() {
        userService.say();
        return "Hello World!";
    }
}
