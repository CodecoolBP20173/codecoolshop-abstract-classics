package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.ConnectionManager;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {

    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoJdbc instance;
     Connection connection;

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

    @Override
    public void add(Supplier supplier) {

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
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {

        Supplier supplier = null;

        String query = "SELECT * FROM supplier;";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                supplier = new Supplier(name, description);
                data.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
