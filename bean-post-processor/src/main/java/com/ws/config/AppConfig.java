package com.ws.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gl on 2017/9/18.
 */

@Configuration
@ComponentScan(basePackages = "com.ws")
public class AppConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper(JsonModule jsonModule) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .indentOutput(true)
                .simpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                );

        return builder.build()
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true)
                .registerModule(jsonModule)
                .addHandler(new DeserializationProblemHandler() {
                    @Override
                    public Object handleWeirdStringValue(DeserializationContext ctxt, Class<?> targetType, String valueToConvert, String failureMsg) throws IOException {
                        if(BigDecimal.class.equals(targetType)){
                        }
                        if(Date.class.equals(targetType)){
                        }
                        return null;
                    }

                    @Override
                    public Object handleWeirdNumberValue(DeserializationContext ctxt, Class<?> targetType, Number valueToConvert, String failureMsg) throws IOException {
                        return null;
                    }
                });

    }

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("http://127.0.0.1:6379");
        return Redisson.create(config);
    }

//    @Bean
//    public ProxyFactoryBean proxyFactoryBean(){
//        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
//        factoryBean.setBeanFactory();
//    }
}
