package com.boneix.mybatis.dao;

import com.boneix.mybatis.entity.User;

import java.util.List;

/**
 * Created by rzhang on 2017/3/8.
 */
public interface UserDao {
    public void insert(User user);

    public User findUserById (int userId);

    public List<User> findAllUsers();
}
