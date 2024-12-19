package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.OverviewDTO;
import com.prod.facades.IOverviewFacade;
import com.prod.models.products.Overview;
import com.prod.services.products.IOverviewService;
import com.prod.utils.ConvertListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OverviewFacade implements IOverviewFacade {
    @Autowired
    private IOverviewService overviewService;
    @Autowired
    private ConvertListPage<Overview> convertListPage;

    @Override
    public ResponseObject<Overview> createOverview(OverviewDTO overview) {
        Overview _overview = overviewService.createOverview(
                Overview.builder()
                        .product_id(overview.getProduct_id())
                        .user_id(overview.getUser_id())
                        .build()
        );
        return ResponseObject.<Overview>builder()
                .data(_overview)
                .message("Tao overview thanh cong")
                .isSuccess(true)
                .build();
    }

    @Override
    public ResponseObject<Overview> getOverview(OverviewDTO overview) {
        Optional<Overview> _overview = overviewService.getOverviewById(overview.getId());
        if (_overview.isEmpty())
            return ResponseObject.<Overview>builder()
                    .message("Khong tim thay overview")
                    .build();
        else return ResponseObject.<Overview>builder()
                .data(_overview.get())
                .message("Tim thay overview")
                .isSuccess(true)
                .build();
    }

    @Override
    public ResponseObject<Page<Overview>> getAllOverviewsByProductId(OverviewDTO overviewDTO, int page, int size, String field, String direct) {
        List<Overview> overviews = overviewService.getPageOverviewsByProductId(
                overviewDTO.getProduct_id(), page, size, field, direct
        ).getContent();
        if (overviews.isEmpty()) {
            return ResponseObject.<Page<Overview>>builder()
                    .message("Khong tim thay danh sach overview cua san pham")
                    .build();
        } else return ResponseObject.<Page<Overview>>builder()
                .data(convertListPage.listToPage(overviews, page, size))
                .message("Da tim thay danh sach overview cua san pham")
                .isSuccess(true)
                .build();
    }

    @Override
    public ResponseObject<Page<Overview>> getAllOverviewsByUserId(OverviewDTO overviewDTO, int page, int size, String field, String direct) {
        List<Overview> overviews = overviewService.getPageOverviewsByUserId(
                overviewDTO.getUser_id(), page, size, field, direct
        ).getContent();
        if (overviews.isEmpty()) {
            return ResponseObject.<Page<Overview>>builder()
                    .message("Khong tim thay danh sach overview cua nguoi dung")
                    .build();
        } else return ResponseObject.<Page<Overview>>builder()
                .data(convertListPage.listToPage(overviews, page, size))
                .message("Da tim thay danh sach overview cua nguoi dung")
                .isSuccess(true)
                .build();
    }
}
