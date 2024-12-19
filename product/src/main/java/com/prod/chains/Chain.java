package com.prod.chains;

import com.prod.chains.DTO.ChainDataDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class Chain<T> {
    private ChainHandler<T> chainHandler;
    private final List<ChainHandler<T>> chainHandlers = new ArrayList<>();
    public Chain(ChainHandler<T> chain){
        this.chainHandler = chain;
    }
    public Chain<T> add(ChainHandler<T> chainHandler) {
        this.chainHandler = chainHandler;
        chainHandlers.add(chainHandler);
        return this;
    }
    public void execute(ChainDataDTO<T> chainDataDTO) {
        chainHandlers.forEach(c -> c.handle(chainDataDTO));
    }
}
