package com.ws.dto;

/**
 * create by gl
 * on 2018/5/2
 */
public class UserDto {

    private String name;

    private int age;

    public UserDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
