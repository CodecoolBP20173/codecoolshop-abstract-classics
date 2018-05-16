package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.config.ConnectionManager;
import com.codecool.shop.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoJdbc {

    private static OrderDaoJdbc instance = null;
    private static Connection connection;

    private OrderDaoJdbc() {}

    public static OrderDaoJdbc getInstance() {
        if (instance == null) {
            instance = new OrderDaoJdbc();

            try {
                connection = ConnectionManager.getInstance().getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

}
