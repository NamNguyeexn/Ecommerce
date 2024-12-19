package com.prod.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConvertListPage<T> {
    public Page<T> listToPage(List<T> list, int page, int size) {
        page = page - 1;
        Pageable pageable = PageRequest.of(page, size);
        int start = Math.min((int) pageable.getOffset(), list.size());
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
    public List<T> pageToList(Page<T> page) {
        return page.getContent();
    }
}
