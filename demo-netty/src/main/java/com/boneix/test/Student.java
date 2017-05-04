package com.boneix.test;

import java.io.Serializable;

/**
 * Created by rzhang on 2017/3/6.
 */
public class Student implements Serializable{

    private static final long serialVersionUID = -8707087697539601751L;
    private int id;
    private String name;

    public Student() {
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
