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

public class OrderDaoJdbc implements OrderDao {

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


    @Override
    public void add(Order order) {
        String ordersQuery = "INSERT INTO orders (name, description, email, phone, billing_address, shipping_address, payment_method)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        String orderProductsQuery = "INSERT INTO order_products (order_id, product_id, quantity) VALUES (?, ?, ?);";

        String lastOrderIdQuery = "SELECT MAX(id) AS last_id FROM orders;";

        try {
            PreparedStatement ordersStatement = connection.prepareStatement(ordersQuery);
            ordersStatement.setString(1, order.getCustomerName());
            ordersStatement.setString(2, order.getDescription());
            ordersStatement.setString(3, order.getCustomerEmail());
            ordersStatement.setString(4, order.getCustomerPhone());
            ordersStatement.setString(5, order.getCustomerBillingAddress());
            ordersStatement.setString(6, order.getCustomerShippingAddress());
            ordersStatement.setString(7, order.getCustomerPaymentMethod());
            ordersStatement.executeUpdate();


            PreparedStatement lastOrderIdStatement = connection.prepareStatement(lastOrderIdQuery);
            ResultSet resultSet = lastOrderIdStatement.executeQuery();
            resultSet.next();
            int lastOrderId = resultSet.getInt("last_id");


            Map<Integer, Integer> orderProducts = order.getLineItems();

            for (Map.Entry<Integer,Integer> products: orderProducts.entrySet()) {
                Integer productId = products.getKey();
                Integer quantity = products.getValue();

                PreparedStatement orderProductsStatement = connection.prepareStatement(orderProductsQuery);
                orderProductsStatement.setInt(1, lastOrderId);
                orderProductsStatement.setInt(2, productId);
                orderProductsStatement.setInt(3, quantity);
                orderProductsStatement.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public int getNumberOfOrders() {
        String query = "SELECT COUNT(id) AS orderCount FROM orders;";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt("orderCount");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public boolean noOrderPlaced() {
        return getNumberOfOrders() == 0;
    }
}
