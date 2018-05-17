package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/","/*"})
public class ProductController extends HttpServlet {

    private int addedId = -1;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        SupplierDao productSupplierStore = SupplierDaoJdbc.getInstance();
        OrderDao orderDataStore = OrderDaoJdbc.getInstance();
        HttpSession session = req.getSession();
        int sessionUserId;

        if (session == null || session.getAttribute("userId") == null) {
            sessionUserId = 0;
        } else {
            sessionUserId = (Integer) session.getAttribute("userId");
        }

        int orderId = sessionUserId;

        int itemsInCart;
        Map<Integer,Integer> lineItems;
        Map<Product,Integer> popoverItems = new HashMap<>();

        if (orderDataStore.noOrderPlacedForUser(sessionUserId)) {
            itemsInCart = 0;
        } else {
            Order order = orderDataStore.find(orderId);
            itemsInCart = order.getNumberOfItems();

            lineItems = order.getLineItems();

            for (Map.Entry<Integer,Integer> p: lineItems.entrySet()) {
                Integer key = p.getKey();
                Integer value = p.getValue();

                popoverItems.put(productDataStore.find(key), value);
            }
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

        int subTotal = 0;
        for (Map.Entry<Product,Integer> p: popoverItems.entrySet()) {
            Product key = p.getKey();
            Integer value = p.getValue();

            subTotal += (key.getDefaultPrice()*value);
        }



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
        if (addedId != -1) {
            context.setVariable("active", "active");
            context.setVariable("popupContentName", productDataStore.find(addedId).getName());
            context.setVariable("popupContentId", productDataStore.find(addedId).getId());



            addedId = -1;
        }
        context.setVariable("lineItems", popoverItems);
        context.setVariable("subTotal", subTotal);
        System.out.println("get");
        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        int productToAddId = Integer.valueOf(req.getParameter("add-button"));
        HttpSession session = req.getSession();
        int sessionUserId = (Integer) session.getAttribute("userId");
        int orderId = sessionUserId;

        OrderDao orderDataStore = OrderDaoJdbc.getInstance();

        if (orderDataStore.noOrderPlacedForUser(sessionUserId)) {

            String orderName = "Order-" + orderId;
            Order order = new Order(orderName, orderId);
            order.addItem(productToAddId);
            orderDataStore.add(order);

        } else {
            Order order = orderDataStore.find(orderId);
            Product productToAdd = productDataStore.find(productToAddId);
            order.addItem(productToAddId);
            ((OrderDaoJdbc) orderDataStore).updateOrderProducts(order, productToAdd);
        }
        addedId = productToAddId;
        String currentURI = req.getParameter("current-uri");
        resp.sendRedirect(currentURI);
        System.out.println("post");
    }
}
