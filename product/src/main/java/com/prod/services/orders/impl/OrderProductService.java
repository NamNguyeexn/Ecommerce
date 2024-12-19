package com.prod.services.orders.impl;

import com.prod.JPARepositories.orders.OrderProductRepository;
import com.prod.models.orders.Order_Product;
import com.prod.services.orders.IOrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.orders.OrderProductRepository.Specs.*;

@Service
public class OrderProductService implements IOrderProductService {
    @Autowired
    private OrderProductRepository repository;
    @Override
    public Order_Product createOrderProduct(Order_Product orderProduct) {
        if (orderProduct.getCreate_at() != orderProduct.getUpdate_at()) {
            orderProduct.setUpdate_at(LocalDateTime.now());
        }
        return repository.save(orderProduct);
    }

    @Override
    public Optional<Order_Product> getOrderProductById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Order_Product> getOrderProductsByOrderId(int orderId) {
        return repository.findAll(byOrderId(orderId));
    }

    @Override
    public List<Order_Product> getOrderProductsByCartProductId(int cartProductId) {
        return repository.findAll(byCartProductId(cartProductId));
    }

    @Override
    public void deleteOrderProductById(int id) {
        repository.deleteById(id);
    }
}
