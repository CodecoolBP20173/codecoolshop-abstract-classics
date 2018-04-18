package com.codecool.shop.controller;

import com.codecool.shop.Cart.CartItems;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Iterator;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart/*", "/cart"})
public class ShopCartServlet extends HttpServlet {



    @Override
    public void init() throws ServletException {
        super.init();
        Map<Product,Integer> products = CartItems.cartItems;
        ProductDao productDataStore = ProductDaoMem.getInstance();
        CartItems.addItem(productDataStore.find(2));
        CartItems.addItem(productDataStore.find(2));
        CartItems.addItem(productDataStore.find(1));
        CartItems.addItem(productDataStore.find(3));
        CartItems.addItem(productDataStore.find(3));
        CartItems.addItem(productDataStore.find(3));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String buttonValue = req.getParameter("addRemove");
        removeAddCart(buttonValue);
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("shoppingItems", CartItems.cartItems);
        resp.sendRedirect("/cart");
        System.out.println(CartItems.cartItemList.size());


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(CartItems.cartItemList.size());
        int subTotal = 0;
        for (Map.Entry<Product,Integer> p: CartItems.cartItems.entrySet()) {
            Product key = p.getKey();
            Integer value = p.getValue();

            subTotal += (key.getDefaultPrice()*value);
        }


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("shoppingItems", CartItems.cartItems);
        context.setVariable("subTotal", subTotal);
        engine.process("product/cart.html", context, resp.getWriter());


    }

    private void removeAddCart(String button) {


        Iterator<Map.Entry<Product,Integer>> it = CartItems.cartItems.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Product, Integer> pair = it.next();
            Product key = pair.getKey();
            if (button.equals("add" + key.getId())) {
                CartItems.increaseItemNumber(key);
            } else if (button.equals("remove" + key.getId())) {
                CartItems.decreaseItemNumber(key, it);
            }
        }

    }

}
