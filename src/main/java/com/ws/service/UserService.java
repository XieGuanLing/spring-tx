package com.ws.service;

import com.ws.annotation.RoutingSwitch;
import com.ws.bean.UserEntry;

/**
 * Created by gl on 2017/9/16.
 */
public interface UserService {

    void save(UserEntry userEntry);

    /**
     * 选择哪个版本
     */
    @RoutingSwitch("ImplV1")
    void say();
}
