package com.prod.services.details;

import com.prod.models.details.Detail;
import com.prod.models.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IDetailService {
    //create
    Detail createDetail(Detail detail);
    //get
    Optional<Detail> getDetailById(int id);
    Optional<Detail> getDetailByProductId(int productId);
    List<Detail> getDetailsByCategoryId(int categoryId);
    List<Detail> getDetailsBySeasonId(int seasonId);
    Page<Detail> getPageDetailsByCategoryId(int categoryId, int page, int size, String sortField, String sortDirect);
    Page<Detail> getPageDetailsBySeasonId(int seasonId, int page, int size, String sortField, String sortDirect);
    Page<Detail> getPageDetailsByPageWithMultiCondition(int page, int size, int cate, int season, List<Integer> label, String sortDirection);
    //delete
    void deleteDetailById(int id);
    void deleteDetail(Detail detail);
}
