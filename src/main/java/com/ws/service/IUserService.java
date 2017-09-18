package com.ws.service;

import com.ws.annotation.RoutingSwitch;
import com.ws.bean.UserEntry;

/**
 * Created by gl on 2017/9/16.
 */
public interface IUserService {
    void save(UserEntry userEntry);
    @RoutingSwitch("ImplV1")
    void say();
}
