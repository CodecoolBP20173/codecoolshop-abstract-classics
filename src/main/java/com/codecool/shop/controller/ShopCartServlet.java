package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart/*", "/cart"})
public class ShopCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoJdbc.getInstance();

        Order order = orderDataStore.find(1);
        Map<Integer,Integer> orderLineItems = order.getLineItems();
        Map<Product,Integer> shoppingItems = new HashMap<>();

        int subTotal = 0;
        for (Map.Entry<Integer,Integer> p: orderLineItems.entrySet()) {
            Integer key = p.getKey();
            Integer value = p.getValue();

            shoppingItems.put(productDataStore.find(key), value);

            subTotal += (productDataStore.find(key).getDefaultPrice() * value);
        }

        String buttonValue = req.getParameter("addRemove");
        removeAddCart(buttonValue);
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("shoppingItems", shoppingItems);
        resp.sendRedirect("/cart");


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        Map<Integer,Integer> orderLineItems;
        Map<Product,Integer> shoppingItems = new HashMap<>();

        if (orderDataStore.noOrderPlaced()) {
            orderLineItems = new HashMap<>();
        } else {
            Order order = orderDataStore.find(1);
            orderLineItems = order.getLineItems();
        }

        int subTotal = 0;
        for (Map.Entry<Integer,Integer> p: orderLineItems.entrySet()) {
            Integer key = p.getKey();
            Integer value = p.getValue();

            shoppingItems.put(productDataStore.find(key), value);

            subTotal += (productDataStore.find(key).getDefaultPrice() * value);
        }


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("shoppingItems", shoppingItems);
        context.setVariable("subTotal", subTotal);
        engine.process("product/cart.html", context, resp.getWriter());


    }

    private void removeAddCart(String button) {
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        Order order = orderDataStore.find(1);

        Iterator<Map.Entry<Integer,Integer>> it = order.getLineItems().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Integer> pair = it.next();
            Integer key = pair.getKey();
            if (button.equals("add" + key)) {
                order.addItem(key);
            } else if (button.equals("remove" + key)) {
                order.decreaseItemNumber(key, it);
            }
        }

        if (order.getNumberOfItems() == 0) {
            orderDataStore.remove(order.getId());
        }
    }

}
