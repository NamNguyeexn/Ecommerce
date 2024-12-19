package com.prod.controllers;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.facades.DTO.ColorDTO;
import com.prod.facades.DTO.SeasonDTO;
import com.prod.facades.ISeasonFacade;
import com.prod.models.carts.Color;
import com.prod.models.details.Season;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/season")
public class SeasonController extends Controller<Season> {
    @Autowired
    private ISeasonFacade seasonFacade;
    @Override
    public ResponseEntity<ResponseObject<List<Season>>> notOwners() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<List<Season>>builder()
                        .message(NOT_OWNER)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseObject<List<Season>>> serverErrors() {
        return null;
    }

    private ResponseEntity<ResponseObject<Page<Season>>> serverErrorP() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<Page<Season>>builder()
                        .message(SERVER_ERROR)
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<Season>> createSeason(@RequestBody SeasonDTO seasonDTO,
                                                               HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(
                        seasonFacade.createSeason(seasonDTO)
                );
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseObject<Season>> getSeasonById(@RequestParam int id) {
        try {
            return ResponseEntity.ok().body(
                    seasonFacade.getSeasonById(id)
            );
        } catch (Exception e) {
            return serverError(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject<Page<Season>>> getAllSeasons(@RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(defaultValue = "10") int limit,
                                                                      @RequestParam(defaultValue = "id") String sortField,
                                                                      @RequestParam(defaultValue = "incr") String sortDirect) {
        try {
            return ResponseEntity.ok().body(
                    seasonFacade.getAllSeasons(page, limit, sortField, sortDirect)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @GetMapping("/byYear")
    public ResponseEntity<ResponseObject<Page<Season>>> getSeasonsByYear(@RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int limit,
                                                                         @RequestParam(defaultValue = "id") String sortField,
                                                                         @RequestParam(defaultValue = "incr") String sortDirect,
                                                                         @RequestParam(defaultValue = "2024") String year) {
        try {
            return ResponseEntity.ok().body(
                    seasonFacade.getAllSeasonsByYear(year, page, limit, sortField, sortDirect)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverErrorP();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject<Season>> updateColor(@RequestBody SeasonDTO seasonDTO,
                                                              HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                ResponseObject<Season> rsp =  seasonFacade.updateSeason(seasonDTO);
                if(rsp.isSuccess()) {
                    return ResponseEntity.ok().body(
                            rsp
                    );
                }
                else{
                    return ResponseEntity.badRequest().body(
                            rsp
                    );
                }
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }

//    @GetMapping("/delete")
//    public ResponseEntity<ResponseObject<Boolean>> deleteSeasonById(@RequestBody SeasonDTO seasonDTO,
//                                                                    HttpServletRequest request) {
//        try {
//            AccountDTO accountDTO = getAccount(request);
//            if (accountDTO == null) return ResponseEntity.badRequest().body(
//                    ResponseObject.<Boolean>builder()
//                            .message(NOT_FOUND_ACCOUNT)
//                            .build()
//            );
//            if (accountDTO.getRole().toString().equals("ADMIN")) {
//                return ResponseEntity.ok().body(
//                        seasonFacade.deleteSeason(seasonDTO)
//                );
//            } else return ResponseEntity.badRequest().body(
//                    ResponseObject.<Boolean>builder()
//                            .message(NOT_OWNER)
//                            .build()
//            );
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return ResponseEntity.badRequest().body(
//                    ResponseObject.<Boolean>builder()
//                            .message(SERVER_ERROR)
//                            .build()
//            );
//        }
//    }


}
