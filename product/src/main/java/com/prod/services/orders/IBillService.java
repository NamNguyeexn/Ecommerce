package com.prod.services.orders;

import com.prod.models.orders.Bill;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IBillService {
    Bill createBill(Bill bill);
    Optional<Bill> getBillById(int id);
    List<Bill> getBills();
    Optional<Bill> getBillByOrderId(int orderId);
    List<Bill> getBillsByStatus(String status);
}
