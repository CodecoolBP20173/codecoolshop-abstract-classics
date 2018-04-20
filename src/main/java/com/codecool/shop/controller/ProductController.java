package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/","/*"})
public class ProductController extends HttpServlet {

    private int addedId = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao productSupplierStore = SupplierDaoMem.getInstance();
        OrderDao orderDataStore = OrderDaoMem.getInstance();

        int itemsInCart;

        if (orderDataStore.noOrderPlaced()) {
            itemsInCart = 0;
        } else {
            Order order = orderDataStore.find(1);
            itemsInCart = order.getNumberOfItems();
        }

//        Map params = new HashMap<>();
//        params.put("category", productCategoryDataStore.find(1));
//        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        // This context is specific to Web Applications
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariables(params);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", productSupplierStore.getAll());
        context.setVariable("itemsInCart", itemsInCart);


        String queryString = req.getQueryString();
        if (queryString != null) {
            if (req.getQueryString().contains("category")) {
                int categoryId = Integer.valueOf(req.getParameter("category"));
                context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(categoryId)));
            } else if (req.getQueryString().contains("supplier")) {
                int supplierId = Integer.valueOf(req.getParameter("supplier"));
                context.setVariable("products", productDataStore.getBy(productSupplierStore.find(supplierId)));
            }
        } else {
            context.setVariable("products", productDataStore.getAll());
        }
        context.setVariable("active", "passive");
        if (addedId != 0) {
            context.setVariable("active", "active");
            context.setVariable("popupContentName", productDataStore.find(addedId).getName());
            context.setVariable("popupContentId", productDataStore.find(addedId).getId());
            addedId = 0;
        }

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        int productToAddId = Integer.valueOf(req.getParameter("add-button"));

        OrderDao orderDataStore = OrderDaoMem.getInstance();

        if (orderDataStore.noOrderPlaced()) {

            int numberOfOrders = orderDataStore.getNumberOfOrders();
            String orderName = "Order-" + (numberOfOrders + 1);
            Order order = new Order(orderName);
            orderDataStore.add(order);
            order.addItem(productDataStore.find(productToAddId));

        } else {
            Order order = orderDataStore.find(1);
            order.addItem(productDataStore.find(productToAddId));
        }
        addedId = productToAddId;
        String currentURI = req.getParameter("current-uri");
        resp.sendRedirect(currentURI);
    }
}
