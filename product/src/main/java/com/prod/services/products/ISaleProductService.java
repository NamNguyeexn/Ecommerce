package com.prod.services.products;

import com.prod.models.products.Sale_Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ISaleProductService {
    //create
    Sale_Product createSaleProduct(Sale_Product saleProduct);
    List<Sale_Product> createSaleProducts(List<Sale_Product> saleProducts);
    //read
    Optional<Sale_Product> getSaleProductById(int id);
    List<Sale_Product> getSaleProductsByProductId(int productId);
    Page<Sale_Product> getPageSaleProductsByProductId(int productId, int page, int size, String sortDirect, String sortField);
    Optional<Sale_Product> getSaleProductsBySaleId(int saleId);
    //delete
    void deleteSaleProduct(int id);
    void deleteSaleProductsBySaleId(int saleId);
    void deleteSaleProductsByProductId(int productId);
}
