package com.prod.facades;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.SeasonDTO;
import com.prod.models.details.Season;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISeasonFacade {
    ResponseObject<Season> createSeason(SeasonDTO seasonDTO);
    ResponseObject<Season> updateSeason(SeasonDTO seasonDTO);
    ResponseObject<Season> getSeasonById(int id);
    ResponseObject<Page<Season>> getAllSeasons(int page, int size, String field, String direct);
    ResponseObject<Page<Season>> getAllSeasonsByYear(String year, int page, int size, String field, String direct);
    ResponseObject<Boolean> deleteSeason(SeasonDTO seasonDTO);
}
