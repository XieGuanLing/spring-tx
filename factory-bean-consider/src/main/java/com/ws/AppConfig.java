package com.ws;

import com.ws.http.HttpFactoryBean;
import com.ws.http.LocalHttpRequest;
import org.springframework.context.annotation.Bean;

public class AppConfig {
    /**
     * 如果两个DateStringFactoryBean的name相同， 则回报
     * No property editor [java.util.DateEditor] found for type java.util.Date according to 'Editor' suffix convention
     * @return
     */
    @Bean(name = "dateFactoryBean")
    public DateStringFactoryBean createString(){
        DateStringFactoryBean bean = new DateStringFactoryBean();
        bean.setDate(true);
        return bean;
    }

    @Bean(name = "stringFactoryBean")
    public DateStringFactoryBean createDate(){
        DateStringFactoryBean bean = new DateStringFactoryBean();
        bean.setDate(false);
        return bean;
    }


    @Bean(name = "localHttpClient")
    public HttpFactoryBean createLocalHttpClient(){
        return new HttpFactoryBean(LocalHttpRequest.class);
    }


}
