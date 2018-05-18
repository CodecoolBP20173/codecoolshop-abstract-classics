package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {

    private List<Order> orders = new ArrayList<>();
    private static OrderDaoMem instance = null;

    private OrderDaoMem() {}

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }

        return instance;
    }

    @Override
    public void add(Order order) {
        order.setId(orders.size() + 1);
        orders.add(order);
    }

    @Override
    public Order find(int id) {
        return orders.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        orders.remove(find(id));
    }

    @Override
    public List<Order> getAll() {
        return orders;
    }

    @Override
    public boolean noOrderPlacedForUser(int userId) {
        int numberOfOrders = 0;
        for (Order order: orders) {
            if (order.getId() == userId) {
                numberOfOrders++;
            }
        }
        return numberOfOrders == 0;
    }
}
