package com.ws.service;

import com.ws.annotation.RoutingSwitch;
import com.ws.bean.UserEntry;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Created by gl on 2017/9/16.
 */
@Validated
public interface UserService {

    void save(@NotNull UserEntry userEntry);

    /**
     * 选择哪个版本
     * todo:程序自动根据不同环境切换, 类似spring的Profile注解
     */
    @RoutingSwitch("ImplV1")
    void say();
}
