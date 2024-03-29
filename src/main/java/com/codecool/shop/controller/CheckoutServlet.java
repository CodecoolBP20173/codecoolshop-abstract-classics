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
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoJdbc.getInstance();
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        HttpSession session = req.getSession();
        int sessionUserId;

        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("/");
        } else {
            sessionUserId = (Integer) session.getAttribute("userId");

            int orderId = sessionUserId;

            if (orderDataStore.noOrderPlacedForUser(sessionUserId)) {
                resp.sendRedirect("/cart");

            } else {
                Order order = orderDataStore.find(orderId);

                int subTotal = 0;
                for (Map.Entry<Integer, Integer> p : order.getLineItems().entrySet()) {
                    Integer key = p.getKey();
                    Integer value = p.getValue();

                    subTotal += (productDataStore.find(key).getDefaultPrice() * value);
                }

                TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
                WebContext context = new WebContext(req, resp, req.getServletContext());

                context.setVariable("shoppingItems", order.getLineItems());
                context.setVariable("subTotal", subTotal);
                context.setVariable("name", order.getCustomerName());
                context.setVariable("email", order.getCustomerEmail());
                context.setVariable("phone", order.getCustomerPhone());
                context.setVariable("billingAddress", order.getCustomerBillingAddress());
                context.setVariable("shippingAddress", order.getCustomerShippingAddress());
                context.setVariable("paymentMethod", order.getCustomerPaymentMethod());

                engine.process("product/checkout.html", context, resp.getWriter());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDao orderDataStore = OrderDaoJdbc.getInstance();
        HttpSession session = req.getSession();
        int sessionUserId;

        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("/");
        } else {
            sessionUserId = (Integer) session.getAttribute("userId");
            int orderId = sessionUserId;

            if (!orderDataStore.noOrderPlacedForUser(sessionUserId)) {
                Order order = orderDataStore.find(orderId);
                order.setCustomerName(req.getParameter("name"));
                order.setCustomerEmail(req.getParameter("email"));
                order.setCustomerPhone(req.getParameter("phone"));
                order.setCustomerBillingAddress(req.getParameter("billingAddress"));
                order.setCustomerShippingAddress(req.getParameter("shippingAddress"));
                order.setCustomerPaymentMethod(req.getParameter("paymentMethod"));
                ((OrderDaoJdbc) orderDataStore).updateOrderAfterCheckout(order);
            }

            String currentURI = "/payment";
            resp.sendRedirect(currentURI);
        }
    }
}
