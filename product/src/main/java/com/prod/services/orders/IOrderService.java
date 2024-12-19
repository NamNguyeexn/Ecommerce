package com.prod.services.orders;

import com.prod.models.orders.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface IOrderService {
    //create
    Order createOrder(Order order);
    //get
    Optional<Order> getOrderById(int id);
    Order updateStatus(Order order);
    List<Order> getOrders();
    Page<Order> getPageOrders(int page, int size);
    List<Order> getOrdersByUserId(int userId);
    List<Order> getOrdersByStatus(String orderStatus);
    List<Order> getOrdersByDate(LocalDateTime dateTime);
    List<Order> getOrdersByTotalGreaterThan(long min);
    List<Order> getOrdersByTotalLessThan(long max);
    List<Order> getOrdersByTotalBetween(long min, long max);
    void deleteOrderById(int id);
}
