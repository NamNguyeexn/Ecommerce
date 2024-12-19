package com.prod.services.orders;

import com.prod.models.orders.Order_Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IOrderProductService {
    //create
    Order_Product createOrderProduct(Order_Product orderProduct);
    //get
    Optional<Order_Product> getOrderProductById(int id);
    List<Order_Product> getOrderProductsByOrderId(int orderId);
    List<Order_Product> getOrderProductsByCartProductId(int cartProductId);
    //delete
    void deleteOrderProductById(int id);
}
