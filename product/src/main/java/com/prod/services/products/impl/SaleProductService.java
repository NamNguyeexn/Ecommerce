package com.prod.services.products.impl;

import com.prod.JPARepositories.products.SaleProductRepository;
import com.prod.models.products.Sale_Product;
import com.prod.services.ServicePage;
import com.prod.services.products.ISaleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.products.SaleProductRepository.Specs.*;

@Service
public class SaleProductService extends ServicePage<Sale_Product> implements ISaleProductService {
    @Autowired
    private SaleProductRepository repository;

    @Override
    public Sale_Product createSaleProduct(Sale_Product saleProduct) {
        if (saleProduct.getCreate_at() != saleProduct.getUpdate_at()) {
            saleProduct.setUpdate_at(LocalDateTime.now());
        }
        return repository.save(saleProduct);
    }

    @Override
    public List<Sale_Product> createSaleProducts(List<Sale_Product> saleProducts) {
        return repository.saveAll(saleProducts);
    }

    @Override
    public Optional<Sale_Product> getSaleProductById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Sale_Product> getSaleProductsByProductId(int productId) {
        return repository.findAll(byProductId(productId));
    }

    @Override
    public Page<Sale_Product> getPageSaleProductsByProductId(int productId, int page, int size, String sortDirect, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        return repository.findAll(byProductId(productId), pageable);
    }

    @Override
    public Optional<Sale_Product> getSaleProductsBySaleId(int saleId) {
        return repository.findOne(bySaleId(saleId));
    }

    @Override
    public void deleteSaleProduct(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteSaleProductsBySaleId(int saleId) {
        repository.delete(bySaleId(saleId));
    }

    @Override
    public void deleteSaleProductsByProductId(int productId) {
        repository.delete(byProductId(productId));
    }
}
