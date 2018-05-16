package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.ConnectionManager;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoJdbc  implements ProductDao{
    private static ProductDaoJdbc instance;
    private Connection connection;

    private ProductDaoJdbc() {
        try {
            connection = ConnectionManager.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        String query = "INSERT INTO product (name," +
                                            " description," +
                                            " default_price," +
                                            " default_currency," +
                                            " product_category_id," +
                                            " supplier_id," +
                                            " product_image) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getDefaultPrice());
            preparedStatement.setString(4, product.getDefaultCurrency().toString());
            preparedStatement.setInt(5, ProductCategoryDaoJdbc.getInstance().findByName(product.getProductCategory().getName()));
            preparedStatement.setInt(6, SupplierDaoJdbc.getInstance().findByName(product.getSupplier().getName()));
            preparedStatement.setString(7, "picture_uri_placeholder");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        Product product = null;
        String query = "SELECT * FROM product WHERE id=?;";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                float defaultPrice = rs.getFloat("default_price");
                String currency = rs.getString("default_currency");
                ProductCategory productCategory = ProductCategoryDaoJdbc.getInstance().find(rs.getInt("product_category_id"));
                Supplier supplier = SupplierDaoJdbc.getInstance().find(rs.getInt("supplier_id"));
                String productImageUrl = rs.getString("product_image");
                product = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                product.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM product WHERE id=?;";
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
    public List<Product> getAll() {
        List<Product> data = new ArrayList<>();
        String query = "SELECT * FROM product;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                float defaultPrice = rs.getFloat("default_price");
                String currency = rs.getString("default_currency");
                ProductCategory productCategory = ProductCategoryDaoJdbc.getInstance().find(rs.getInt("product_category_id"));
                Supplier supplier = SupplierDaoJdbc.getInstance().find(rs.getInt("supplier_id"));
                Product product = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                product.setId(id);
                data.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> data = new ArrayList<>();
        String query = "SELECT * FROM product" +
                "       WHERE supplier_id = ?;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setInt(1, supplier.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                float defaultPrice = rs.getFloat("default_price");
                String currency = rs.getString("default_currency");
                ProductCategory productCategory = ProductCategoryDaoJdbc.getInstance().find(rs.getInt("product_category_id"));
                supplier = SupplierDaoJdbc.getInstance().find(rs.getInt("supplier_id"));
                Product product = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                data.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> data = new ArrayList<>();
        String query = "SELECT * FROM product" +
                "       WHERE product_category_id = ?;";

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(query);
            preparedStatement.setInt(1, productCategory.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                float defaultPrice = rs.getFloat("default_price");
                String currency = rs.getString("default_currency");
                productCategory = ProductCategoryDaoJdbc.getInstance().find(rs.getInt("product_category_id"));
                Supplier supplier = SupplierDaoJdbc.getInstance().find(rs.getInt("supplier_id"));
                Product product = new Product(name, defaultPrice, currency, description, productCategory, supplier);
                data.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public int getNumberOfProducts() {
        String query = "SELECT COUNT(id) AS productCount FROM product;";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt("productCount");
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
