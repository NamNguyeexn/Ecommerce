package com.prod.facades.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorSizeQuantityDTO {
    private String color;
    private String code;
    private String size;
    private int color_id;
    private int size_id;
    private int quantity;
    @Builder.Default
    private int sold = 0;
}
