package com.prod.chains;

import com.prod.chains.DTO.ChainDataDTO;
import org.springframework.stereotype.Component;

@Component
public interface ChainHandler<T> {
    Chain<T> handle(ChainDataDTO<T> chainDataDTO);
}
