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
import java.util.Map.Entry;


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

        // testing context
        /*List<Product> products = new ArrayList<>();

        ProductDao productDataStore = ProductDaoMem.getInstance();
        Product addedItem = productDataStore.find(1);
        products.add(addedItem);
        addedItem = productDataStore.find(2);
        products.add(addedItem);
        products.add(addedItem);
        addedItem = productDataStore.find(3);
        products.add(addedItem);*/



        /*
        * HashMap<String, HashMap> selects = new HashMap<String, HashMap>();

for(Map.Entry<String, HashMap> entry : selects.entrySet()) {
    String key = entry.getKey();
    HashMap value = entry.getValue();

    // do what you have to do here
    // In your case, another loop.
}
        * */

/*        String title = "Cart";
        String itemsString = "";
        PrintWriter out = resp.getWriter();
        for (Map.Entry<Product,Integer> p: products.entrySet()) {
            Product key = p.getKey();
            Integer value = p.getValue();
            itemsString = itemsString + "<tr><td>" + key.getName() + "</td>" + "<td>" + key.getPrice() + "</td>" + "<td>"+ value + "</td>" +
                    "</tr>";
        }


        out.println(
                "<html>\n" +
                        "<head><title>" + title + "</title></head>\n" +
                        "<body>\n" +
                        "<h1 align = \"center\">" + title + "</h1>\n" +
                        "<table border=1 cellpadding=4 style=\"border-collapse:collapse;\">" +
                        "<tr>"+"<th>Name</th>" + "<th>Unit Price</th>" + "<th>Quantity</th>" + "</tr>" + itemsString + "</table>" +
                        "<br><br>" +
                        "<font size=\"6\"><b>Sum of Price: " + "SUm" + "</b></font>" +
                        "</body></html>"
        );*/
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
