package com.ws.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * create by gl
 * on 2018/6/20
 */
@Service
public class LockTestService {

    Logger logger = LoggerFactory.getLogger(LockTestService.class);

    @NeedLock("appId_")
    public void update(@KeyParam String appId){
        logger.info(appId);
    }
}
