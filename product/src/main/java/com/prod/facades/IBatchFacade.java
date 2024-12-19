package com.prod.facades;

import com.common.DTO.ResponseObject;
import org.springframework.stereotype.Service;

@Service
public interface IBatchFacade {
    ResponseObject<String> insertDetail();
}
