package com.prod.chains.createOrder;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.CSPCartDTO;
import com.prod.models.ENUM.Order_Status;
import com.prod.models.carts.Small_Quantity;
import com.prod.services.carts.ISmallQuantityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CheckIfCSPEnough implements ChainHandler<CSPCartDTO> {
    private final ISmallQuantityService smallQuantityService;
    @Override
    public Chain<CSPCartDTO> handle(ChainDataDTO<CSPCartDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()){
            CSPCartDTO dto = chainDataDTO.getValue();
            Optional<Small_Quantity> smallQuantity = smallQuantityService.getByCSProductId(dto.getCsp_id());
            if (smallQuantity.isPresent()){
                int quantity = smallQuantity.get().getQuantity();
                if (quantity > dto.getQuantity()){
                    if (Objects.equals(chainDataDTO.getOrderStatus(), Order_Status.CHO_XAC_NHAN.toString())){
                        smallQuantityService.update(smallQuantity.get(), dto.getQuantity());
                        chainDataDTO.setOrderStatus(Order_Status.CHO_VAN_CHUYEN.toString());
                    } else{
                        smallQuantityService.update(smallQuantity.get(), dto.getQuantity());
                        chainDataDTO.setOrderStatus(Order_Status.CHO_XAC_NHAN.toString());
                    }
                    chainDataDTO.setSuccess(true);
                } else {
                    chainDataDTO
                            .setMessage("Khong du san pham " + dto.getName() + " trong kho")
                            .setSuccess(false);
                }
            } else {
                chainDataDTO
                        .setMessage("Khong tim thay so luong san pham " + dto.getName() + " trong kho")
                        .setSuccess(false);
            }
        }
        return new Chain<>(this);
    }
}
