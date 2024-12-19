package com.prod.chains.DTO;

import com.prod.models.ENUM.Order_Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChainDataDTO<T> {
    //chain
    private T value;
    private String message;
    @Builder.Default
    private boolean success = true;
    //user
    private int userId;
    private String userEmail;
    private String userName;
    private String address;
    private String phone;
    //order
    private String orderStatus;
    //cart
    private int cartId;

    public ChainDataDTO<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    public ChainDataDTO<T> setValue(T value) {
        this.value = value;
        return this;
    }
}
