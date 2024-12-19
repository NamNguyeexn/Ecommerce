package com.prod.services.products;

import com.prod.models.products.Sale;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ISaleService {
    //create
    Sale createSale(Sale sale);

    //get
    Optional<Sale> getSaleById(int id);

    List<Sale> getSalesBySeasonId(int seasonId);

    Page<Sale> getPageSalesBySeasonId(int seasonId, int page, int size, String field, String direct);

    List<Sale> getSalesByValueGreaterThan(int value);

    List<Sale> getSalesByDateInPass();

    Page<Sale> getPageSalesByDateInPass(int page, int size, String field, String direct);

    List<Sale> getSalesByDateInFuture();

    Page<Sale> getPageSalesByDateInFuture(int page, int size, String field, String direct);

    List<Sale> getSalesByDateBetween(LocalDateTime start, LocalDateTime end);

    Page<Sale> getPageSalesByDateBetween(LocalDateTime start, LocalDateTime now, int page, int size, String field, String direct);

    List<Sale> getSalesByNow();

    Page<Sale> getPageSalesByNow(int page, int size, String field, String direct);

    List<Sale> getSalesByValueLessThan(int value);

    List<Sale> getSalesByValueInRange(int min, int max);

    //update
    Sale updateSale(Sale sale);

    //delete
    void deleteSaleById(int id);

    void deleteSaleBySeasonId(int seasonId);
}
