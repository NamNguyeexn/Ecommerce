package com.prod.services.products.impl;

import com.prod.JPARepositories.products.OverviewRepository;
import com.prod.models.products.Overview;
import com.prod.services.ServicePage;
import com.prod.services.products.IOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.products.OverviewRepository.Specs.*;

@Service
public class OverviewService extends ServicePage<Overview> implements IOverviewService {
    @Autowired
    private OverviewRepository repository;

    @Override
    public Overview createOverview(Overview overview) {
        if (overview.getCreate_at() != overview.getUpdate_at()) {
            overview.setUpdate_at(LocalDateTime.now());
        }
        return repository.save(overview);
    }

    @Override
    public Optional<Overview> getOverviewById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Overview> getOverviewsByUserId(int userId) {
        return repository.findAll(byUserId(userId));
    }

    @Override
    public List<Overview> getOverviewsByProductId(int productId) {
        return repository.findAll(byProductId(productId));
    }

    @Override
    public Page<Overview> getPageOverviewsByProductId(int productId, int page, int size, String field, String direct) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(direct), field);
        return repository.findAll(byProductId(productId), pageable);
    }

    @Override
    public Page<Overview> getPageOverviewsByUserId(int userId, int page, int size, String field, String direct) {
        Pageable pageable = PageRequest.of(page - 1, size, getSortDirect(direct), field);
        return repository.findAll(byUserId(userId), pageable);
    }

    @Override
    public void deleteOverviewById(int id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteOverview(Overview overview) {
        repository.delete(overview);
    }
}
