package com.codecool.shop.controller;

import java.sql.*;

public class Validate
{
    public static boolean checkUser(String username,String pass)
    {
        boolean st =false;
        try{

            //loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");

            //creating connection with the database
            Connection con=DriverManager.getConnection
                    ("jdbc:postgresql://localhost:5432/codecoolshop","soma","Vartoksoma!1");
            PreparedStatement ps =con.prepareStatement
                    ("select * from users where username=? and pass=?");
            ps.setString(1, username);
            ps.setString(2, pass);
            ResultSet rs =ps.executeQuery();
            st = rs.next();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return st;
    }
}