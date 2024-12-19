package com.prod.facades.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private int cspId;
    private String size;
    private String colors;
    private String code;
    private String product;
    private long price;
    private int quantity;
    private int productId;
    private int colorId;
    private int sizeId;
    private String imageSrc;
    private int cart_product_id;
}