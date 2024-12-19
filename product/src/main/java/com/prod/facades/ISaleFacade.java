package com.prod.facades;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.SaleDTO;
import com.prod.models.products.Sale;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public interface ISaleFacade {
    ResponseObject<Sale> createSale(SaleDTO saleDTO);
    ResponseObject<Sale> updateSaleProduct(SaleDTO saleDTO);
    ResponseObject<Sale> getSaleById(int id);
    ResponseObject<Page<Sale>> getAllSalesBySeasonId(SaleDTO saleDTO, int page, int size);
    ResponseObject<Page<Sale>> getAllSalesInPass(int page, int size);
    ResponseObject<Page<Sale>> getAllSalesInFuture(int page, int size);
    ResponseObject<Page<Sale>> getAllSalesBetweenDate(SaleDTO saleDTO, int page, int size);
    ResponseObject<Page<Sale>> getAllSalesByNow(int page, int size);
    ResponseObject<Sale> deleteSaleById(SaleDTO saleDTO);
}
