package com.boneix.dks.domain;

import com.boneix.base.domain.BaseEntity;

/**
 * Created by rzhang on 2017/3/9.
 */
public class User extends BaseEntity {


    private static final long serialVersionUID = 6123437975457684536L;

    String name;
    String password;
    int age;

    public User() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
