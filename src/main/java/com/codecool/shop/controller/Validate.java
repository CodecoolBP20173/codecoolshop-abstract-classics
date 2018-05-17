package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.UserDaoJdbc;
import com.codecool.shop.model.User;
import com.codecool.shop.utils.Utils;

public class Validate
{
    public static boolean checkUser(String username,String pass)
    {
        boolean st =false;
        try{
            UserDaoJdbc udjdbc = UserDaoJdbc.getInstance();
            User user = udjdbc.findByName(username);
            st = Utils.checkPasswords(pass, user.getPassword());
            System.err.println(st);
            return st;

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return st;
    }
}