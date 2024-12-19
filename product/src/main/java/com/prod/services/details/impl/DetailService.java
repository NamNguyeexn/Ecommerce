package com.prod.services.details.impl;

import com.prod.JPARepositories.details.DetailRepository;
import com.prod.models.details.Detail;
import com.prod.models.products.Label_Product;
import com.prod.services.ServicePage;
import com.prod.services.details.IDetailService;
import com.prod.services.products.ILabelProductService;
import com.prod.utils.ConvertListPage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.prod.JPARepositories.details.DetailRepository.Specs.*;

@Service
public class DetailService extends ServicePage<Detail> implements IDetailService {
    @Autowired
    private DetailRepository repository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ConvertListPage<Detail> convertListPage;
    @Autowired
    private ILabelProductService labelProductService;

    @Override
    public Detail createDetail(Detail detail) {
        return repository.save(detail);
    }

    @Override
    public Optional<Detail> getDetailById(int id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Detail> getDetailByProductId(int productId) {
        return repository.findOne(byProductId(productId));
    }

    @Override
    public List<Detail> getDetailsByCategoryId(int categoryId) {
        return repository.findAll(byCategoryId(categoryId));
    }

    @Override
    public List<Detail> getDetailsBySeasonId(int seasonId) {
        return repository.findAll(bySeasonId(seasonId));
    }

    @Override
    public Page<Detail> getPageDetailsByCategoryId(int category_id, int page, int size, String sortField, String sortDirect) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        if (category_id == 0) return repository.findAll(pageable);
        return repository.findAll(byCategoryId(category_id), pageable);
    }

    @Override
    public Page<Detail> getPageDetailsBySeasonId(int seasonId, int page, int size, String sortField, String sortDirect) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
        if (seasonId == 0) return repository.findAll(pageable);
        return repository.findAll(bySeasonId(seasonId), pageable);
    }

    @Override
    public Page<Detail> getPageDetailsByPageWithMultiCondition(int page, int size, int cate, int season, List<Integer> label, String sortDirection) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(sortDirection), "id");
        Set<Label_Product> labelProducts = new HashSet<>();
        for (int id : label) {
            List<Label_Product> list = labelProductService.getLabelProductsByLabelId(id);
            labelProducts.addAll(list);
        }
        List<Integer> labelProductIds = labelProducts.stream().map(Label_Product::getProduct_id).toList();
        if (cate == 0 && season == 0 && label.isEmpty()) {
            return repository.findAll(pageable);
        } else if (cate == 0 && season == 0 && label.size() != 0) {
            return repository.findAll(byLabelsId(labelProductIds), pageable);
        } else if (cate == 0 && season != 0 && label.isEmpty()) {
            return repository.findAll(bySeasonId(season), pageable);
        } else if (cate == 0 && season != 0 && label.size() != 0) {
            Specification<Detail> specification = Specification.where(
                    bySeasonId(season)
                            .and(byLabelsId(labelProductIds)));
            return repository.findAll(specification, pageable);
        } else if (cate != 0 && season == 0 && label.isEmpty()) {
            return repository.findAll(byCategoryId(cate), pageable);
        } else if (cate != 0 && season == 0 && label.size() != 0) {
            Specification<Detail> specification = Specification.where(
                    byCategoryId(cate)
                            .and(byLabelsId(labelProductIds))
            );
            return repository.findAll(specification, pageable);
        } else if (cate != 0 && season != 0 && label.isEmpty()) {
            Specification<Detail> specification = Specification.where(
                    byCategoryId(cate)
                            .and(bySeasonId(season))
            );
            return repository.findAll(specification, pageable);
        } else {
            Specification<Detail> specification = Specification.where(
                    byCategoryId(cate)
                            .and(bySeasonId(season))
                            .and(byLabelsId(labelProductIds))
            );
            return repository.findAll(specification, pageable);
        }
    }


    @Override
    public void deleteDetailById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteDetail(Detail detail) {
        repository.delete(detail);
    }
}
