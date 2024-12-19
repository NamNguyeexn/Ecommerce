package com.prod.services.orders.impl;

import com.prod.JPARepositories.orders.OrderRepository;
import com.prod.models.ENUM.Order_Status;
import com.prod.models.orders.Order;
import com.prod.services.ServicePage;
import com.prod.services.orders.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.orders.OrderRepository.Specs.*;

@Service
public class OrderService extends ServicePage<Order> implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Order createOrder(Order order) {
        if (order.getCreate_at() != order.getUpdate_at()) {
            order.setUpdate_at(LocalDateTime.now());
        }
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order updateStatus(Order order) {
        Order_Status status = Order_Status.valueOf(order.getStatus());
        if (status == Order_Status.CHO_XAC_NHAN) {
            status = Order_Status.CHO_VAN_CHUYEN;
        } else if (status == Order_Status.CHO_VAN_CHUYEN) {
            status = Order_Status.DANG_GIAO;
        } else if (status == Order_Status.DANG_GIAO) {
            status = Order_Status.GIAO_HANG_THANH_CONG;
        }
        Order updateOrder = Order.builder()
                .id(order.getId())
                .status(status.toString())
                .total(order.getTotal())
                .date(order.getDate())
                .user_id(order.getUser_id())
                .create_at(order.getCreate_at())
                .update_at(LocalDateTime.now())
                .build();
        return orderRepository.save(updateOrder);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> getPageOrders(int page, int size) {
        Pageable pageable = getPage(page, size, "desc", "date");
        return orderRepository.findAll(pageable);
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return orderRepository.findAll(byUserId(userId));
    }

    @Override
    public List<Order> getOrdersByStatus(String orderStatus) {
        return orderRepository.findAll(byStatus(orderStatus));
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime dateTime) {
        return orderRepository.findAll(byOrderDate(dateTime));
    }

    @Override
    public List<Order> getOrdersByTotalGreaterThan(long min) {
        return orderRepository.findAll(byOrderTotalGreaterThan(min));
    }

    @Override
    public List<Order> getOrdersByTotalLessThan(long max) {
        return orderRepository.findAll(byOrderTotalLessThan(max));
    }

    @Override
    public List<Order> getOrdersByTotalBetween(long min, long max) {
        return orderRepository.findAll(byOrderInRange(min, max));
    }

    @Override
    public void deleteOrderById(int id) {
        orderRepository.deleteById(id);
    }
}
