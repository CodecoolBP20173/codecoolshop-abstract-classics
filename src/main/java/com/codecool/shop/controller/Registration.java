package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.UserDaoJdbc;
import com.codecool.shop.model.User;
import com.codecool.shop.utils.Utils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/registration"})
public class Registration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect("/login");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hashedpw = Utils.hashPassword(password);
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String billing_address = request.getParameter("billing_address");
        String shipping_address = request.getParameter("shipping_address");
        User user = new User(username, hashedpw, phone, email, billing_address, shipping_address);

        UserDaoJdbc udjdbc = UserDaoJdbc.getInstance();
        udjdbc.add(user);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebContext context = new WebContext(request, response, request.getServletContext());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        engine.process("product/registration.html",context, response.getWriter());
    }



}
