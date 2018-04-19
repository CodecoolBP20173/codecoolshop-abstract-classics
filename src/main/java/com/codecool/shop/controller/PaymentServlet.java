package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
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
import java.util.Map;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        Order order = orderDataStore.find(1);

        int subTotal = 0;
        for (Map.Entry<Product, Integer> p : order.getLineItems().entrySet()) {
            Product key = p.getKey();
            Integer value = p.getValue();

            subTotal += (key.getDefaultPrice() * value);
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("shoppingItems", order.getLineItems());
        context.setVariable("subTotal", subTotal);

        engine.process("product/payment.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*customerData.put("name", "");
        customerData.put("email", "");
        customerData.put("phone", "");
        customerData.put("billingAddress", "");
        customerData.put("shippingAddress", "");*/

        OrderDaoMem orderDaoMem = OrderDaoMem.getInstance();
        Order order = orderDaoMem.find(1);
        order.setCustomerName(req.getParameter("name"));
        order.setCustomerEmail(req.getParameter("email"));
        order.setCustomerPhone(req.getParameter("phone"));
        order.setCustomerBillingAddress(req.getParameter("billingAddress"));
        order.setCustomerShippingAddress(req.getParameter("shippingAddress"));

        System.out.println(order.getCustomerName());
        String currentURI = "/payment";
        resp.sendRedirect(currentURI);

    }
}