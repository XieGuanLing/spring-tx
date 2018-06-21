package com.ws.controller;

import com.ws.annotation.RoutingInject;
import com.ws.dto.AppIdCommand;
import com.ws.dto.QueryUserCommand;
import com.ws.dto.UserDto;
import com.ws.lock.LockTestService;
import com.ws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * create by gl
 * on 2018/4/21
 */
@Controller
@ResponseBody
public class UserController {


    /**
     * 某个方法需要动态切换
     */
    @RoutingInject
    private UserService userService;

    @Autowired
    private LockTestService lockTestService;


    @RequestMapping("/appId")
    public String appId(@RequestBody AppIdCommand command) {
        lockTestService.update(command.getAppId());
        return command.getAppId();
    }



    @RequestMapping("/")
    public String home() {
        userService.say();
        return "Hello World!";
    }

    @RequestMapping("bar")
    public String bar() throws InterruptedException, IOException {
        Random random = new Random();
        int sleep= random.nextInt(100);
        TimeUnit.MILLISECONDS.sleep(sleep);
        return " [service3 sleep " + sleep+" ms]";
    }

    @RequestMapping("tar")
    public String tar() throws InterruptedException, IOException {
        Random random = new Random();
        int sleep= random.nextInt(1000);
        TimeUnit.MILLISECONDS.sleep(sleep);
        return " [service4 sleep " + sleep+" ms]";
    }

    @RequestMapping("users")
    public UserDto users(@RequestBody QueryUserCommand command) {
        System.out.println(command);
        return new UserDto("gl", 28);
    }

}