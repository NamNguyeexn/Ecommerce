package com.prod.services.products;

import com.prod.models.products.Overview;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IOverviewService {
    //create
    Overview createOverview(Overview overview);
    //read
    Optional<Overview> getOverviewById(int id);
    List<Overview> getOverviewsByUserId(int userId);
    List<Overview> getOverviewsByProductId(int productId);
    Page<Overview> getPageOverviewsByProductId(int productId, int page, int size, String field, String direct);
    Page<Overview> getPageOverviewsByUserId(int userId, int page, int size, String field, String direct);
    //delete
    void deleteOverviewById(int id);
    void deleteOverview(Overview overview);
}
