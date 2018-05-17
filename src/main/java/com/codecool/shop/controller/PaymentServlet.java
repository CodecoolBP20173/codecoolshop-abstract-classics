package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoJdbc;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoJdbc;
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

@WebServlet(urlPatterns = {"/payment"})
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoJdbc.getInstance();
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        Map<Integer,Integer> orderLineItems;
        Map<Product,Integer> shoppingItems = new HashMap<>();
        HttpSession session = req.getSession();
        int sessionUserId = (Integer) session.getAttribute("userId");
        int orderId = sessionUserId;


        if (orderDataStore.noOrderPlacedForUser(sessionUserId)) {
            resp.sendRedirect("/");

        } else {
            Order order = orderDataStore.find(orderId);
            orderLineItems = order.getLineItems();

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
            context.setVariable("paymentMethod", order.getCustomerPaymentMethod());

            engine.process("product/payment.html", context, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoJdbc.getInstance();
        HttpSession session = req.getSession();
        int sessionUserId = (Integer) session.getAttribute("userId");
        int orderId = sessionUserId;
        orderDataStore.remove(orderId);

        resp.sendRedirect("/");
    }
}