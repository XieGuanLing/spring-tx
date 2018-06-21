package com.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by gl
 * on 2018/6/18
 */
@Service
public class RedisTransactionService {

    @Autowired
    private RedisTemplate template;

//    @Autowired
//    @Qualifier("stringRedisTemplate")
//    private StringRedisTemplate stringRedisTemplate;


    public void getA(){
        System.out.println(template.opsForValue().get("foo"));
    }


    /**
     * org.springframework.data.redis.RedisSystemException: Unknown redis exception; nested exception is java.lang.NullPointerException
     */
    public void setA(){
        try {
            template.opsForValue().set("foo", "bar");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void tx(){
        Object o = template.execute(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForSet().add("tx", "value1");

                // This will contain the results of all ops in the transaction
                return operations.exec();
            }
        });
        System.out.println("Number of items added to set: " + o);
    }


}
