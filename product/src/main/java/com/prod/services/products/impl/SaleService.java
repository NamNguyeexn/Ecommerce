package com.prod.services.products.impl;

import com.prod.JPARepositories.products.SaleRepository;
import com.prod.models.products.Sale;
import com.prod.services.ServicePage;
import com.prod.services.products.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.products.SaleRepository.Specs.*;

@Service
public class SaleService extends ServicePage<Sale> implements ISaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Override
    public Sale createSale(Sale sale) {
        if (sale.getCreate_at() != sale.getUpdate_at()) {
            sale.setUpdate_at(LocalDateTime.now());
        }
        return saleRepository.save(sale);
    }

    @Override
    public Optional<Sale> getSaleById(int id) {
        return saleRepository.findById(id);
    }

    @Override
    public List<Sale> getSalesBySeasonId(int seasonId) {
        return saleRepository.findAll(bySeasonId(seasonId));
    }

    @Override
    public Page<Sale> getPageSalesBySeasonId(int seasonId, int page, int size, String field, String direct) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(direct), field);
        return saleRepository.findAll(bySeasonId(seasonId), pageable);
    }

    @Override
    public List<Sale> getSalesByValueGreaterThan(int value) {
        return saleRepository.findAll(byValueGreaterThan(value));
    }

    @Override
    public List<Sale> getSalesByDateInPass() {
        return saleRepository.findAll(byDateBefore(LocalDateTime.now()));
    }

    @Override
    public Page<Sale> getPageSalesByDateInPass(int page, int size, String field, String direct) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(direct), field);
        return saleRepository.findAll(byDateBefore(LocalDateTime.now()), pageable);
    }

    @Override
    public List<Sale> getSalesByDateInFuture() {
        return saleRepository.findAll(byDateAfter(LocalDateTime.now()));
    }

    @Override
    public Page<Sale> getPageSalesByDateInFuture(int page, int size, String field, String direct) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(direct), field);
        return saleRepository.findAll(byDateAfter(LocalDateTime.now()), pageable);
    }

    @Override
    public List<Sale> getSalesByDateBetween(LocalDateTime start, LocalDateTime end) {
        return saleRepository.findAll(byDateBetween(start, end));
    }

    @Override
    public Page<Sale> getPageSalesByDateBetween(LocalDateTime start, LocalDateTime end, int page, int size, String field, String direct) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(direct), field);
        return saleRepository.findAll(byDateBetween(start, end), pageable);
    }

    @Override
    public List<Sale> getSalesByNow() {
        List<Sale> sales = saleRepository.findAll();
        List<Sale> res = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getStart().isBefore(LocalDateTime.now()) &&
                    sale.getEnd().isAfter(LocalDateTime.now())) {
                res.add(sale);
            }
        }
        return res;
    }

    @Override
    public Page<Sale> getPageSalesByNow(int page, int size, String field, String direct) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(direct), field);
        return saleRepository.findAll(byNow(), pageable);
    }

    @Override
    public List<Sale> getSalesByValueLessThan(int value) {
        return saleRepository.findAll(byValueLessThan(value));
    }

    @Override
    public List<Sale> getSalesByValueInRange(int min, int max) {
        return saleRepository.findAll(byValueInRange(min, max));
    }

    @Override
    public Sale updateSale(Sale sale) {
        return null;
    }

    @Override
    public void deleteSaleById(int id) {
        saleRepository.deleteById(id);
    }

    @Override
    public void deleteSaleBySeasonId(int seasonId) {
        saleRepository.delete(bySeasonId(seasonId));
    }
}
