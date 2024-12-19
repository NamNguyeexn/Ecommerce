package com.prod.services.orders.impl;

import com.prod.JPARepositories.orders.BillRepository;
import com.prod.models.orders.Bill;
import com.prod.services.orders.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.orders.BillRepository.Specs.*;

@Service
public class BillService implements IBillService {
    @Autowired
    private BillRepository billRepository;
    @Override
    public Bill createBill(Bill bill) {
        if (bill.getCreate_at() != bill.getUpdate_at()) {
            bill.setUpdate_at(LocalDateTime.now());
        }
        return billRepository.save(bill);
    }

    @Override
    public Optional<Bill> getBillById(int id) {
        return billRepository.findById(id);
    }

    @Override
    public List<Bill> getBills() {
        return billRepository.findAll();
    }

    @Override
    public Optional<Bill> getBillByOrderId(int orderId) {
        return billRepository.findOne(byOrderId(orderId));
    }

    @Override
    public List<Bill> getBillsByStatus(String status) {
        return billRepository.findAll(byStatus(status));
    }
}
