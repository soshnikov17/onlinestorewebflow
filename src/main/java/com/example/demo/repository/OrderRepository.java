package com.example.demo.repository;

import com.example.demo.model.OrderModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private final List<OrderModel> orders = new ArrayList<>();

    public void save(OrderModel order) {
        orders.add(order);
    }

    public List<OrderModel> findAll() {
        return orders;
    }
}
