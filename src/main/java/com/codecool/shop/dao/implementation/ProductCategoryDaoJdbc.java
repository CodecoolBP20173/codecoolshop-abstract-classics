package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.ConnectionManager;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductCategoryDaoJdbc implements ProductCategoryDao {
    private static ProductCategoryDaoJdbc instance;
    private Connection connection;

    private ProductCategoryDaoJdbc() {
        try {
            connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ProductCategoryDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory productCategory) {
        String query = "INSERT INTO product_category (name, description, department) VALUES (?, ?, ?);";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setString(1, productCategory.getName());
            preparedStatement.setString(2, productCategory.getDescription());
            preparedStatement.setString(3, productCategory.getDepartment());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) {
        ProductCategory productCategory = null;
        String query = "SELECT * FROM product_category WHERE id=?;";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                String department = rs.getString("department");
                productCategory = new ProductCategory(name, department, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productCategory;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM product_category WHERE id=?;";
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
    public List<ProductCategory> getAll() {
        List<ProductCategory> data = new ArrayList<>();
        String query = "SELECT * FROM product_category;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                String department = rs.getString("department");
                ProductCategory productCategory = new ProductCategory(name, department, description);
                data.add(productCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}



