package com.codecool.shop.model;

import java.util.*;

public class Order extends BaseModel {

    private List<Product> itemList = new ArrayList<>();
    private Map<Product,Integer> lineItems = new HashMap<>();
    private Map<String, String> customerData;

    public Order(String name) {
        super(name);
        this.itemList = new ArrayList<>();
        this.lineItems = new HashMap<>();
        this.customerData = new HashMap<>();
        customerData.put("name", "");
        customerData.put("email", "");
        customerData.put("phone", "");
        customerData.put("billingAddress", "");
        customerData.put("shippingAddress", "");
    }

    public void addItem (Product product) {
        itemList.add(product);
        increaseItemNumber(product);
    }

    public void increaseItemNumber(Product product) {
        if (lineItems.containsKey(product)) {
            lineItems.put(product, lineItems.get(product) + 1);
        } else {
            lineItems.put(product, 1);
        }
    }

    public void decreaseItemNumber(Product product, Iterator iterator) {
        if (lineItems.get(product) > 0) {
            lineItems.put(product, lineItems.get(product) -1);
            itemList.remove(product);
        }

        if (lineItems.get(product) == 0) {
            iterator.remove();
            itemList.remove(product);
        }
    }

    public Map<Product, Integer> getLineItems() {
        return lineItems;
    }

    public Map<String, String> getCustomerData() {
        return customerData;
    }
}
