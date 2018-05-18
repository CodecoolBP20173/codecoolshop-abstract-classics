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
        String ordersQuery = "INSERT INTO orders (name, description, email, phone, billing_address, shipping_address, payment_method, id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

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
            ordersStatement.setInt(8, order.getId());
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
        String orderQuery = "SELECT * FROM orders WHERE id = ?;";

        String orderProductsQuery = "SELECT * FROM order_products WHERE order_id = ?;";

        try {
            Map<Integer, Integer> orderProducts = new HashMap<>();
            PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
            orderStatement.setInt(1, id);
            ResultSet orderResultSet = orderStatement.executeQuery();

            if (orderResultSet.next()) {
                Order order = setOrderObject(orderResultSet);
                PreparedStatement orderProductStatement = connection.prepareStatement(orderProductsQuery);
                orderProductStatement.setInt(1, id);
                ResultSet orderProductsResultSet = orderProductStatement.executeQuery();

                while(orderProductsResultSet.next()) {
                    int productId = orderProductsResultSet.getInt("product_id");
                    int quantity = orderProductsResultSet.getInt("quantity");

                    orderProducts.put(productId, quantity);
                }

                for (Map.Entry<Integer,Integer> products: orderProducts.entrySet()) {
                    Integer productId = products.getKey();
                    Integer quantity = products.getValue();

                    for (int i = 0; i < quantity; i++) {
                        order.addItem(productId);
                    }
                }
                return order;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(int id) {
        String orderProductsQuery = "DELETE FROM order_products WHERE order_id = ?;";
        String ordersQuery = "DELETE FROM orders WHERE id = ?;";

        try {
            PreparedStatement orderProductsStatement = connection.prepareStatement(orderProductsQuery);
            orderProductsStatement.setInt(1, id);
            orderProductsStatement.executeUpdate();

            PreparedStatement ordersStatement = connection.prepareStatement(ordersQuery);
            ordersStatement.setInt(1, id);
            ordersStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAll() {
        String ordersQuery = "SELECT * FROM orders;";
        List<Order> orders = new ArrayList<>();

        try {
            PreparedStatement orderStatement = connection.prepareStatement(ordersQuery);
            ResultSet orderResultSet = orderStatement.executeQuery();

            while(orderResultSet.next()) {
                Map<Integer, Integer> orderProducts = new HashMap<>();
                Order order = setOrderObject(orderResultSet);

                String orderProductsQuery = "SELECT * FROM order_products WHERE order_id = ?;";
                PreparedStatement orderProductsStatement = connection.prepareStatement(orderProductsQuery);
                orderProductsStatement.setInt(1, order.getId());
                ResultSet orderProductsResultSet = orderStatement.executeQuery();

                while(orderProductsResultSet.next()) {
                    int productId = orderProductsResultSet.getInt("product_id");
                    int quantity = orderProductsResultSet.getInt("quantity");

                    orderProducts.put(productId, quantity);
                }

                for (Map.Entry<Integer,Integer> products: orderProducts.entrySet()) {
                    Integer productId = products.getKey();
                    Integer quantity = products.getValue();

                    for (int i = 0; i < quantity; i++) {
                        order.addItem(productId);
                    }
                }

                orderProductsResultSet.beforeFirst();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public boolean noOrderPlacedForUser(int userId) {
        String query = "SELECT COUNT(id) AS orderCount FROM orders WHERE id = ?;";
        int orderId = userId;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt("orderCount") == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    private Order setOrderObject(ResultSet resultSet) {
        try {
            int orderId = resultSet.getInt("id");
            String orderName = "Order-" + (orderId);
            Order order = new Order(orderName, orderId);
            order.setDescription(resultSet.getString("description"));
            order.setCustomerName(resultSet.getString("name"));
            order.setCustomerEmail(resultSet.getString("email"));
            order.setCustomerPhone(resultSet.getString("phone"));
            order.setCustomerBillingAddress(resultSet.getString("billing_address"));
            order.setCustomerShippingAddress(resultSet.getString("shipping_address"));
            order.setCustomerPaymentMethod(resultSet.getString("payment_method"));

            return order;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateOrderProducts(Order order, Product product) {
        List<Integer> productIds = new ArrayList<>();

        String productIdsQuery = "SELECT product_id FROM order_products WHERE order_id = ?";

        try {
            PreparedStatement productIdsStatement = connection.prepareStatement(productIdsQuery);
            productIdsStatement.setInt(1, order.getId());
            ResultSet resultSet = productIdsStatement.executeQuery();

            while (resultSet.next()) {
                productIds.add(resultSet.getInt("product_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (productIds.contains(product.getId())) {
            String orderProductsQuery = "UPDATE order_products SET quantity = ? WHERE order_id = ? AND product_id = ?";
            try {
                PreparedStatement orderProductsStatement = connection.prepareStatement(orderProductsQuery);
                int newQuantity = order.getLineItems().get(product.getId());
                orderProductsStatement.setInt(1, newQuantity);
                orderProductsStatement.setInt(2, order.getId());
                orderProductsStatement.setInt(3, product.getId());
                orderProductsStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String orderProductsQuery = "INSERT INTO order_products (order_id, product_id, quantity) VALUES (?, ?, ?);";

            try {
                PreparedStatement orderProductsStatement = connection.prepareStatement(orderProductsQuery);
                orderProductsStatement.setInt(1, order.getId());
                orderProductsStatement.setInt(2, product.getId());
                orderProductsStatement.setInt(3, 1);
                orderProductsStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeOrderProduct(Order order, Product product) {
        String orderProductsQuery = "DELETE FROM order_products WHERE order_id = ? AND product_id = ?;";

        try {
            PreparedStatement orderProductsStatement = connection.prepareStatement(orderProductsQuery);
            orderProductsStatement.setInt(1, order.getId());
            orderProductsStatement.setInt(2, product.getId());
            orderProductsStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderAfterCheckout(Order order) {
        String orderUpdateQuery = "UPDATE orders SET name=?, email=?, phone=?, billing_address=?, shipping_address=?, payment_method=? WHERE id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(orderUpdateQuery);
            statement.setString(1, order.getCustomerName());
            statement.setString(2, order.getCustomerEmail());
            statement.setString(3, order.getCustomerPhone());
            statement.setString(4, order.getCustomerBillingAddress());
            statement.setString(5, order.getCustomerShippingAddress());
            statement.setString(6, order.getCustomerPaymentMethod());
            statement.setInt(7, order.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
