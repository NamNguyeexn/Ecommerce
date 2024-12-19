package com.prod.facades;

import com.prod.facades.DTO.BillDTO;
import com.prod.facades.DTO.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public interface IOrderFacade {
    void createOrder(OrderDTO orderDTO);
    BillDTO updateBill(BillDTO billDTO);
    Page<OrderDTO> getOrdersByUserId(int userId, int page, int limit);
    Page<BillDTO> getBillsByUserId(int userId, int page, int limit);
    OrderDTO changeStatus(int id);
    Page<OrderDTO> getAllOrders(int page, int limit);
    Page<OrderDTO> getAllOrdersByStatus(String status, int page, int limit);
}
