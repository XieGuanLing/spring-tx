package com.ws;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.ws.config.SynchronizationConfig;
import com.ws.service.RedisTransactionService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by gl on 2017/9/18.
 */
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
public class SynchronizationApplication {

    public static void main(String[] args) throws InterruptedException {
//        SpringApplication.run(SynchronizationApplication.class, args);
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SynchronizationConfig.class);
        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);
        System.out.println(redisTemplate);

//redisClient();

        RedisTransactionService redisTransactionService = context.getBean(RedisTransactionService.class);
//        redisTransactionService.setA();
        redisTransactionService.tx();


//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        CountDownLatch latch = new CountDownLatch(2);
//
//        executorService.submit(()->{
//            redisTransactionService.setA();
//            latch.countDown();
//        });
//
//        executorService.submit(()->{
//            try {
//                Thread.sleep(10*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }finally {
//                latch.countDown();
//            }
//            redisTransactionService.getA();
//        });
//
//        latch.await();
//        executorService.shutdown();
    }


    public static void  redisClient(){
        RedisClient redisClient = RedisClient.create("redis://localhost:6379");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("key", "Hello, Redis!");

        connection.close();
        redisClient.shutdown();
    }


}