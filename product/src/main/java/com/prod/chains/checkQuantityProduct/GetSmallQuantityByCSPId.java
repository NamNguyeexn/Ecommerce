package com.prod.chains.checkQuantityProduct;

import com.prod.chains.DTO.ChainDataDTO;
import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.facades.DTO.CartDTO;
import com.prod.models.carts.Small_Quantity;
import com.prod.services.caches.IEmailSender;
import com.prod.services.carts.ISmallQuantityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetSmallQuantityByCSPId implements ChainHandler<CartDTO> {
    private final ISmallQuantityService smallQuantityService;
    private final IEmailSender emailSender;
    @Override
    public Chain<CartDTO> handle(ChainDataDTO<CartDTO> chainDataDTO) {
        if(!chainDataDTO.isSuccess()) {
            return new Chain<>(this);
        }
        int csp_id = chainDataDTO.getValue().getCspId();
        Optional<Small_Quantity> s_quantity = smallQuantityService.getByCSProductId(
                csp_id
        );
        if(s_quantity.isPresent()) {
            if (s_quantity.get().getQuantity() == 0) {
                chainDataDTO.setMessage("Het_Hang")
                        .setSuccess(true);
                emailSender.outOfProduct(
                        chainDataDTO.getUserEmail(),
                        chainDataDTO.getValue().getProduct()
                );
            } else if (s_quantity.get().getQuantity() <= 5) {
                chainDataDTO.setMessage("Gan_het_Hang:" + s_quantity.get().getQuantity())
                        .setSuccess(true);
                emailSender.lowQuantityProduct(
                        chainDataDTO.getUserEmail(),
                        chainDataDTO.getValue().getProduct(),
                        s_quantity.get().getQuantity()
                );
            }
        } else {
            chainDataDTO.setMessage("Khong_tim_thay_so_luong_san_pham")
                    .setSuccess(false);
        }
        return new Chain<>(this);
    }
}
