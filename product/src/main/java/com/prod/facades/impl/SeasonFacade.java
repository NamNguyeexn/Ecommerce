package com.prod.facades.impl;

import com.common.DTO.ResponseObject;
import com.prod.facades.DTO.SeasonDTO;
import com.prod.facades.ISeasonFacade;
import com.prod.models.carts.Color;
import com.prod.models.details.Season;
import com.prod.services.details.ISeasonService;
import com.prod.utils.ConvertListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SeasonFacade implements ISeasonFacade {
    @Autowired
    private ISeasonService seasonService;
    @Autowired
    private ConvertListPage<Season> convertListPage;
    @Override
    public ResponseObject<Season> createSeason(SeasonDTO seasonDTO) {
        Optional<Season> season = seasonService.getSeasonByNameAndYear(seasonDTO.getName(), seasonDTO.getYear());
        if (season.isPresent()) {
            return ResponseObject.<Season>builder()
                    .message("Mua da ton tai")
                    .isSuccess(false)
                    .data(season.get())
                    .build();
        } else {
            Season newSeason = Season.builder()
                    .name(seasonDTO.getName())
                    .year(seasonDTO.getYear())
                    .build();
            return ResponseObject.<Season>builder()
                    .data(seasonService.createSeason(newSeason))
                    .isSuccess(true)
                    .message("Tao season thanh cong")
                    .build();
        }
    }

    @Override
    public ResponseObject<Season> updateSeason(SeasonDTO seasonDTO) {
        Optional<Season> season = seasonService.getSeasonById(seasonDTO.getId());
        if (season.isPresent()) {
            season.get().setName(seasonDTO.getName());
            season.get().setYear(seasonDTO.getYear());
            season.get().setUpdate_at(LocalDateTime.now());
            seasonService.createSeason(season.get());
            return ResponseObject.<Season>builder()
                    .message("Cap nhat thanh cong")
                    .isSuccess(true)
                    .data(season.get())
                    .build();
        } else {
            return ResponseObject.<Season>builder()
                    .data(null)
                    .isSuccess(false)
                    .message("cap nhat that bai")
                    .build();
        }

    }

    @Override
    public ResponseObject<Season> getSeasonById(int id) {
        Optional<Season> _season = seasonService.getSeasonById(id);
        if (_season.isPresent()) {
            return ResponseObject.<Season>builder()
                    .data(_season.get())
                    .isSuccess(true)
                    .message("Lay season thanh cong")
                    .build();
        } else return ResponseObject.<Season>builder()
                .message("Khong tim thay season")
                .build();
    }

    @Override
    public ResponseObject<Page<Season>> getAllSeasons(int page, int size, String field, String direct) {
        List<Season> seasons = seasonService.getPageSeasons(page, size, field, direct).getContent();
        if (!seasons.isEmpty()) {
            return ResponseObject.<Page<Season>>builder()
                    .data(convertListPage.listToPage(seasons, page, size))
                    .isSuccess(true)
                    .message("Lay danh sach season thanh cong")
                    .build();
        } else return ResponseObject.<Page<Season>>builder()
                .message("Khong tim thay danh sach season")
                .build();
    }

    @Override
    public ResponseObject<Page<Season>> getAllSeasonsByYear(String year, int page, int size, String field, String direct) {
        List<Season> seasons = seasonService.getPageSeasonsByYear(year, page, size, field, direct).getContent();
        if (!seasons.isEmpty()) {
            return ResponseObject.<Page<Season>>builder()
                    .data(convertListPage.listToPage(seasons, page, size))
                    .isSuccess(true)
                    .message("Lay danh sach season thanh cong")
                    .build();
        } else return ResponseObject.<Page<Season>>builder()
                .message("Khong tim thay danh sach season")
                .build();
    }

    @Override
    public ResponseObject<Boolean> deleteSeason(SeasonDTO seasonDTO) {
        seasonService.deleteSeasonById(seasonDTO.getId());
        boolean res = seasonService.getSeasonById(seasonDTO.getId()).isPresent();
        return ResponseObject.<Boolean>builder()
                .isSuccess(res)
                .message("Da thuc hien xoa season")
                .build();
    }
}
