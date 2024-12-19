package com.prod.chains.getOrders;

import com.prod.DTO.AccountDTO;
import com.prod.DTO.UserDTO;
import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.OrderDTO;
import com.prod.models.orders.Bill;
import com.prod.services.caches.impl.RedisInfoService;
import com.prod.services.orders.IBillService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Builder
public class GetUserDetail implements ChainHandler<OrderDTO> {
    private final IBillService billService;
    @Override
    public Chain<OrderDTO> handle(ChainDataDTO<OrderDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            OrderDTO dto = chainDataDTO.getValue();
            Optional<Bill> bill = billService.getBillByOrderId(dto.getId());
            if (bill.isPresent()){
                dto.setUser_name(bill.get().getUser());
                dto.setAddress_name(bill.get().getAddress());
                dto.setPhone(bill.get().getPhone());
                chainDataDTO.setValue(dto).setSuccess(true);
            } else {
                chainDataDTO.setMessage("Khong tim thay hoa don").setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
}
