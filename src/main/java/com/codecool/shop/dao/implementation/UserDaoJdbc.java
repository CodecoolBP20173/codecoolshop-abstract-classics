package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.ConnectionManager;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJdbc implements UserDao {
    private static UserDaoJdbc instance;
    private static Connection connection;

    private UserDaoJdbc() {
        try {
            connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserDaoJdbc getInstance() {
        if (instance == null) {
            instance = new UserDaoJdbc();
        }
        return instance;
    }


    @Override
    public void add(User user) {
        String query = "INSERT INTO public.users (name, password, email, phone_number, billing_address, shipping_address)" +
                " VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getBillingAddress());
            preparedStatement.setString(6, user.getShippingAddress());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(int id) {
        User user = null;

        String query = "SELECT * FROM users WHERE id=?;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                int phone = rs.getInt("phone_number");
                String billingAddress = rs.getString("billing_address");
                String shippingAddress = rs.getString("shipping_address");

                user = new User(name, password, phone, email, billingAddress, shippingAddress);
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByEmail(String eMail) {
        User user = null;

        String query = "SELECT * FROM users WHERE email=?;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setString(1, eMail);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                long phone = rs.getLong("phone_number");

                String billingAddress = rs.getString("billing_address");
                String shippingAddress = rs.getString("shipping_address");

                user = new User(name, password, phone, email, billingAddress, shippingAddress);
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM users WHERE id=?;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> data = new ArrayList<>();
        String query = "SELECT * FROM users;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                int phone = rs.getInt("phone_number");
                String billingAddress = rs.getString("billing_address");
                String shippingAddress = rs.getString("shipping_address");

                User user = new User(name, password, phone, email, billingAddress, shippingAddress);
                user.setId(id);
                data.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

}
