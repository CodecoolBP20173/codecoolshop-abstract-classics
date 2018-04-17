package com.codecool.shop.Cart;

import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CartItems {


    public static ArrayList<Product> cartItemList = new ArrayList<>();

    public static Map<Product,Integer> cartItems = new HashMap<>();

    public static void addItem (Product product) {
        cartItemList.add(product);
        increaseItemNumber(product);
    }

    public static void removeItem (Product product) {
        if (cartItemList.contains(product)) {
            cartItemList.remove(product);
            decreaseItemNumber(product);
        }

    }


    public static void increaseItemNumber(Product product) {
        if (cartItems.containsKey(product)) {
            cartItems.put(product, cartItems.get(product) + 1);
        } else {
            cartItems.put(product, 1);
        }
    }

    public static void decreaseItemNumber(Product product) {
        if (cartItems.get(product) > 0) {
            cartItems.put(product, cartItems.get(product) -1);
        }

        if (cartItems.get(product) == 0) {
            cartItems.remove(product);
        }
    }
}




