package com.prod.facades.DTO;

import com.prod.validators.LabelValid;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    //product
    private int productId;
    private String title;
    private long price;
    @Builder.Default
    private int reviews = 0;
    @Builder.Default
    private double score = 0.0;
    private int quantity;
    private int sold;
    private String description;
    //label
    @LabelValid
    private List<String> label;
    //category
    private int category;
    private String categoryName;
    //season
    private int season_id;
    private String season;
    //image
    private List<ImageDTO> images;
    //color size quantity
    private List<ColorSizeQuantityDTO> csq;
}
