package com.codecool.shop.Cart;

import com.codecool.shop.model.Product;

import java.util.ArrayList;


public class CartItems {


    public static ArrayList<Product> cartItemList = new ArrayList<>();


    public static void addItem (Product product) {
        cartItemList.add(product);
    }

    public static void removeItem (Product product) {
        cartItemList.remove(product);
    }
}




