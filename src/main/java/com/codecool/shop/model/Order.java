package com.codecool.shop.model;

import java.util.*;

public class Order extends BaseModel {

    private List<Integer> itemList = new ArrayList<>();
    private Map<Integer,Integer> lineItems = new HashMap<>();
    private Map<String, String> customerData;

    public Order(String name, int id) {
        super(name);
        this.description = "";
        this.itemList = new ArrayList<>();
        this.lineItems = new HashMap<>();
        this.customerData = new HashMap<>();
        super.id = id;
        customerData.put("name", "");
        customerData.put("email", "");
        customerData.put("phone", "");
        customerData.put("billingAddress", "");
        customerData.put("shippingAddress", "");
        customerData.put("paymentMethod", "");
    }

    public void addItem (Integer productId) {
        itemList.add(productId);
        increaseItemNumber(productId);
    }

    public void increaseItemNumber(Integer productId) {
        if (lineItems.containsKey(productId)) {
            lineItems.put(productId, lineItems.get(productId) + 1);
        } else {
            lineItems.put(productId, 1);
        }
    }

    public void decreaseItemNumber(Integer productId, Iterator iterator) {
        if (lineItems.get(productId) > 0) {
            lineItems.put(productId, lineItems.get(productId) - 1);
            itemList.remove(productId);
        }

        if (lineItems.get(productId) == 0) {
            iterator.remove();
            itemList.remove(productId);
        }
    }

    public List<Integer> getItemList() {
        return itemList;
    }

    public int getNumberOfItems() {
        return itemList.size();
    }

    public Map<Integer, Integer> getLineItems() {
        return lineItems;
    }

    public Map<String, String> getCustomerData() {
        return customerData;
    }

    public String getCustomerName() {
        return customerData.get("name");
    }

    public void setCustomerName(String name) {
        customerData.replace("name", name);
    }

    public String getCustomerEmail() {
        return customerData.get("email");
    }

    public void setCustomerEmail(String email) {
        customerData.replace("email", email);
    }

    public String getCustomerPhone() {
        return customerData.get("phone");
    }

    public void setCustomerPhone(String phone) {
        customerData.replace("phone", phone);
    }

    public String getCustomerBillingAddress() {
        return customerData.get("billingAddress");
    }

    public void setCustomerBillingAddress(String billingAddress) {
        customerData.replace("billingAddress", billingAddress);
    }

    public String getCustomerShippingAddress() {
        return customerData.get("shippingAddress");
    }

    public void setCustomerShippingAddress(String shippingAddress) {
        customerData.replace("shippingAddress", shippingAddress);
    }

    public String getCustomerPaymentMethod() {
        return customerData.get("paymentMethod");
    }

    public void setCustomerPaymentMethod(String paymentMethod) {
        customerData.replace("paymentMethod", paymentMethod);
    }
}
