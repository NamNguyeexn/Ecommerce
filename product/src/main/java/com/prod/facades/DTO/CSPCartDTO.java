package com.prod.facades.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CSPCartDTO {
    //cart product
    private int quantity;
    private int csp_id;
    //product
    private int product_id;
    private String name;
    private long price;
    private String image;
    private String color;
    private String size;
    //cart
    private int cart_id;
    private int order_id;
}
