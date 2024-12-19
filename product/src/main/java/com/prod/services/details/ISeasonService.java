package com.prod.services.details;

import com.prod.models.details.Season;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ISeasonService {
    //create
    Season createSeason(Season season);
    List<Season> createSeasons(List<Season> seasons);
    //get
    Optional<Season> getSeasonById(int id);
    Optional<Season> getSeasonByNameAndYear(String name, String year);
    List<Season> getAllSeasons();
    List<Season> getSeasonsByYear(String year);
    Page<Season> getPageSeasons(int page, int size, String field, String direct);
    Page<Season> getPageSeasonsByYear(String year, int page, int size, String field, String direct);
    //delete
    void deleteSeasonById(int id);
    void deleteSeasonsByYear(String year);
    void deleteSeason(Season season);
}
