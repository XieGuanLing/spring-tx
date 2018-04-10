package com.ws;

import org.springframework.beans.factory.FactoryBean;

import java.util.Date;

public class DateStringFactoryBean implements FactoryBean<Object> {

    private boolean isDate;

    public void setDate(boolean date) {
        isDate = date;
    }

    @Override
    public Object getObject() {
        return isDate ? new Date() : "2018-03-04";
    }

    @Override
    public Class<?> getObjectType() {
        return isDate ? Date.class : String.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
