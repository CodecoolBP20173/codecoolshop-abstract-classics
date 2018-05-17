package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.UserDaoJdbc;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.User;
import com.codecool.shop.utils.Utils;
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

@WebServlet(urlPatterns = {"/login"})
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("user/user_login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getRequestDispatcher("link.html").include(req, resp);
        UserDaoJdbc userDaoJdbc = UserDaoJdbc.getInstance();


        String email = req.getParameter("inputEmail");
        String password=req.getParameter("inputPassword");
        User user = userDaoJdbc.findByEmail(email);


        if(user != null && Utils.checkPasswords(password, user.getPassword())){
/*            HttpSession session=req.getSession();
            session.setAttribute("id",user.getId());
            session.setAttribute("email",email);
            System.err.println("HHHHHH: " + session.getAttribute("email"));*/
        }
        else{
            req.getRequestDispatcher("login.html").include(req, resp);
        }
    }
}