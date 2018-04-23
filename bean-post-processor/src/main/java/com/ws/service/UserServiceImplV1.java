package com.ws.service;

import com.ws.bean.UserEntry;
import org.springframework.stereotype.Service;

/**
 * Created by gl on 2017/9/16.
 */
@Service
public class UserServiceImplV1 implements UserService {
    @Override
    public void save(UserEntry userEntry) {
        System.out.println("v1");
    }

    @Override
    public void say(){
        System.out.println("v1");
    }

}
