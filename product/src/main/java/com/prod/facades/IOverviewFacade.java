package com.prod.facades;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.OverviewDTO;
import com.prod.models.products.Overview;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public interface IOverviewFacade {
    ResponseObject<Overview> createOverview(OverviewDTO overview);
    ResponseObject<Overview> getOverview(OverviewDTO overview);
    ResponseObject<Page<Overview>> getAllOverviewsByProductId(OverviewDTO overviewDTO, int page, int size, String field, String direct);
    ResponseObject<Page<Overview>> getAllOverviewsByUserId(OverviewDTO overviewDTO, int page, int size, String field, String direct);
}
