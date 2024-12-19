package com.prod.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public abstract class ServicePage<T> {
    public Pageable getPage(int page, int size, String sortDirect, String sortField) {
        return  PageRequest.of(page - 1, size, getSortDirect(sortDirect), sortField);
    }
    public Sort.Direction getSortDirect(String direct) {
        if (direct.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
}
