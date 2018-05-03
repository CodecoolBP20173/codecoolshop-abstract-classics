package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.ConnectionManager;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {

    private static SupplierDaoJdbc instance;
    private static Connection connection;

    private SupplierDaoJdbc() {
        try {
            connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SupplierDaoJdbc getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }

    public static SupplierDaoJdbc getTestInstance(Connection conn) {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
            connection = conn;
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO public.supplier (name, description) VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getDescription());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        Supplier supplier = null;

        String query = "SELECT * FROM supplier WHERE id=?;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                supplier = new Supplier(name, description);
                supplier.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return supplier;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM supplier WHERE id=?;";

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
    public List<Supplier> getAll() {
        List<Supplier> data = new ArrayList<>();
        String query = "SELECT * FROM supplier;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                Supplier supplier = new Supplier(name, description);
                supplier.setId(rs.getInt("id"));
                data.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
