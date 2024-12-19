package com.prod.services.details.impl;

import com.prod.JPARepositories.details.SeasonRepository;
import com.prod.models.details.Season;
import com.prod.services.ServicePage;
import com.prod.services.details.ISeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.prod.JPARepositories.details.SeasonRepository.Specs.*;

@Service
public class SeasonService extends ServicePage<Season> implements ISeasonService {
    @Autowired
    private SeasonRepository seasonRepository;

    @Override
    public Season createSeason(Season season) {
        if (season.getCreate_at() != season.getUpdate_at()) {
            season.setUpdate_at(LocalDateTime.now());
        }
        return seasonRepository.save(season);
    }

    @Override
    public List<Season> createSeasons(List<Season> seasons) {
        List<Season> list = new ArrayList<>();
        for (Season season : seasons) {
            list.add(createSeason(season));
        }
        return list;
    }

    @Override
    public Optional<Season> getSeasonById(int id) {
        return seasonRepository.findById(id);
    }

    @Override
    public Optional<Season> getSeasonByNameAndYear(String name, String year) {
        Specification<Season> specification = Specification.where(byName(name).and(byYear(year)));
        return seasonRepository.findOne(specification);
    }

    @Override
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    @Override
    public List<Season> getSeasonsByYear(String year) {
        return seasonRepository.findAll(byYear(year));
    }

    @Override
    public Page<Season> getPageSeasons(int page, int size, String field, String direct) {
        Pageable pageable = getPage(page, size, direct, field);
        return seasonRepository.findAll(pageable);
    }

    @Override
    public Page<Season> getPageSeasonsByYear(String year, int page, int size, String field, String direct) {
        Pageable pageable = getPage(page, size, direct, field);
        return seasonRepository.findAll(byYear(year), pageable);
    }

    @Override
    public void deleteSeasonById(int id) {
        seasonRepository.deleteById(id);
    }

    @Override
    public void deleteSeasonsByYear(String year) {
        seasonRepository.delete(byYear(year));
    }

    @Override
    public void deleteSeason(Season season) {
        seasonRepository.delete(season);
    }
}
