package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.SaleDTO;
import com.prod.facades.ISaleFacade;
import com.prod.models.products.Sale;
import com.prod.models.products.Sale_Product;
import com.prod.services.products.ISaleProductService;
import com.prod.services.products.ISaleService;
import com.prod.utils.ConvertListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SaleFacade implements ISaleFacade {
    @Autowired
    private ISaleService saleService;
    @Autowired
    private ISaleProductService saleProductService;
    @Autowired
    private ConvertListPage<Sale> convertListPage;
    @Override
    public ResponseObject<Sale> createSale(SaleDTO saleDTO) {
        Sale sale = Sale.builder()
                .season_id(saleDTO.getSeason_id())
                .value(saleDTO.getValue())
                .start(saleDTO.getStart())
                .end(saleDTO.getEnd())
                .build();
        Sale newSale = saleService.createSale(sale);
        Sale_Product saleProduct = Sale_Product.builder()
                .saleId(newSale.getId())
                .build();
        if (saleDTO.getProduct_ids() != null) saleProduct.setProductId(getListProduct(saleDTO));
        saleProductService.createSaleProduct(saleProduct);
        return ResponseObject.<Sale>builder()
                .data(newSale)
                .message("Da tao sale")
                .isSuccess(true)
                .build();
    }

    @Override
    public ResponseObject<Sale> updateSaleProduct(SaleDTO saleDTO) {
        Optional<Sale> _sale = saleService.getSaleById(saleDTO.getId());
        if (_sale.isPresent()) {
            _sale.get().setValue(saleDTO.getValue());
            _sale.get().setStart(saleDTO.getStart());
            _sale.get().setEnd(saleDTO.getEnd());
            _sale.get().setSeason_id(saleDTO.getSeason_id());
            _sale.get().setUpdate_at(LocalDateTime.now());
            Sale updateSale = saleService.createSale(_sale.get());
            Optional<Sale_Product> _saleProduct = saleProductService.getSaleProductsBySaleId(updateSale.getId());
            if (_saleProduct.isPresent()) {
                _saleProduct.get().setProductId(getListProduct(saleDTO));
                _saleProduct.get().setUpdate_at(LocalDateTime.now());
                saleProductService.createSaleProduct(_saleProduct.get());
            }
            return ResponseObject.<Sale>builder()
                    .data(updateSale)
                    .message("Da cap nhat sale")
                    .isSuccess(true)
                    .build();
        } else return ResponseObject.<Sale>builder()
                .message("Khong the cap nhat sale")
                .build();
    }

    @Override
    public ResponseObject<Sale> getSaleById(int id) {
        Optional<Sale> _sale = saleService.getSaleById(id);
        if (_sale.isPresent()) {
            return ResponseObject.<Sale>builder()
                    .data(_sale.get())
                    .message("Da tim thay sale")
                    .isSuccess(true)
                    .build();
        } else return ResponseObject.<Sale>builder()
                .message("Khong the tim thay sale")
                .build();
    }

    @Override
    public ResponseObject<Page<Sale>> getAllSalesBySeasonId(SaleDTO saleDTO, int page, int size) {
        List<Sale> sales = saleService.getSalesBySeasonId(saleDTO.getSeason_id());
        if (sales.isEmpty()) {
            return ResponseObject.<Page<Sale>>builder()
                    .message("Khong tim thay chuong trinh sale trong mua")
                    .build();
        } else return ResponseObject.<Page<Sale>>builder()
                .data(convertListPage.listToPage(sales, page, size))
                .message("Da tim thay cac chuong trinh sale")
                .isSuccess(true)
                .build();
    }

    @Override
    public ResponseObject<Page<Sale>> getAllSalesInPass(int page, int size) {
        List<Sale> sales = saleService.getSalesByDateInPass();
        if (sales.isEmpty()) {
            return ResponseObject.<Page<Sale>>builder()
                    .message("Khong tim thay cac chuong trinh sale trong qua khu")
                    .build();
        } else return ResponseObject.<Page<Sale>>builder()
                .data(convertListPage.listToPage(sales, page, size))
                .isSuccess(true)
                .message("Da tim thay cac chuong trinh sale trong qua khu")
                .build();
    }

    @Override
    public ResponseObject<Page<Sale>> getAllSalesInFuture(int page, int size) {
        List<Sale> sales = saleService.getSalesByDateInFuture();
        if (sales.isEmpty()) {
            return ResponseObject.<Page<Sale>>builder()
                    .message("Khong tim thay cac chuong trinh sale sap toi")
                    .build();
        } else return ResponseObject.<Page<Sale>>builder()
                .data(convertListPage.listToPage(sales, page, size))
                .isSuccess(true)
                .message("Da tim thay cac chuong trinh sale sap toi")
                .build();
    }

    @Override
    public ResponseObject<Page<Sale>> getAllSalesBetweenDate(SaleDTO saleDTO, int page, int size) {
        List<Sale> sales = saleService.getSalesByDateBetween(saleDTO.getStart(), saleDTO.getEnd());
        if (sales.isEmpty()) {
            return ResponseObject.<Page<Sale>>builder()
                    .message("Khong tim thay cac chuong trinh sale trong khoang thoi gian")
                    .build();
        } else return ResponseObject.<Page<Sale>>builder()
                .data(convertListPage.listToPage(sales, page, size))
                .isSuccess(true)
                .message("Da tim thay cac chuong trinh sale trong khoang thoi gian")
                .build();
    }

    @Override
    public ResponseObject<Page<Sale>> getAllSalesByNow(int page, int size) {
        List<Sale> sales = saleService.getSalesByNow();
        if (sales.isEmpty()) {
            return ResponseObject.<Page<Sale>>builder()
                    .message("Khong tim thay cac chuong trinh sale hien tai")
                    .build();
        } else return ResponseObject.<Page<Sale>>builder()
                .data(convertListPage.listToPage(sales, page, size))
                .isSuccess(true)
                .message("Da tim thay cac chuong trinh sale hien tai")
                .build();
    }

    @Override
    public ResponseObject<Sale> deleteSaleById(SaleDTO saleDTO) {
        saleService.deleteSaleById(saleDTO.getId());
        boolean res = saleService.getSaleById(saleDTO.getId()).isEmpty();
        if (res){
            return ResponseObject.<Sale>builder()
                    .message("Xoa chuong trinh sale thanh cong")
                    .isSuccess(true)
                    .build();
        } else return ResponseObject.<Sale>builder()
                .message("Khong the xoa chuong trinh sale")
                .build();
    }
    private String getListProduct(SaleDTO saleDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < saleDTO.getProduct_ids().size() - 1; i++) {
            stringBuilder.append(saleDTO.getProduct_ids().get(i)).append(",");
        }
        stringBuilder.append(saleDTO.getProduct_ids().get(saleDTO.getProduct_ids().size() - 1));
        return stringBuilder.toString();
    }
    private List<Integer> getStringProduct(String product_id) {
        List<Integer> productIds = new ArrayList<>();
        Arrays.stream(product_id.split(",")).toList().forEach(
                str -> productIds.add(Integer.parseInt(str))
        );
        return productIds;
    }
}
