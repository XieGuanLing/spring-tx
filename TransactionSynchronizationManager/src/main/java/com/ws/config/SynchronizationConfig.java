package com.ws.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * create by gl
 * on 2018/6/17
 */
@Configurable
@ComponentScan("com.ws")
public class SynchronizationConfig {
    /**
     * 运行时有两个LettuceConnectionFactory
     * @return
     */
//    @Bean
//    public RedisConnectionFactory lettuceConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master("master")
//                .sentinel("127.0.0.1", 6379) ;
//
//        LettuceConnectionFactory connectionFactory =  new LettuceConnectionFactory();
//        connectionFactory.setValidateConnection(true);
//
//        return new LettuceConnectionFactory();
//    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        LettuceConnectionFactory connectionFactory =  new LettuceConnectionFactory("127.0.0.1", 6379);
        //LettuceConnectionFactory 没有使用Bean注解，不在spring的管理范围，导致afterPropertiesSet回调没有执行， client为空
        connectionFactory.afterPropertiesSet();


        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);

        // explicitly enable transaction support
//        template.setEnableTransactionSupport(true);
        return template;
    }

//    @Bean
//    public RedisTemplate redisTemplate(){
//        RedisTemplate template = new RedisTemplate();
//        template.setConnectionFactory(lettuceConnectionFactory());
//        //template.setEnableTransactionSupport(true);
//        return template;
//    }
}
