package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    User find(int id);
    void remove(int id);
    User findByName(String name);

    List<User> getAll();
}
