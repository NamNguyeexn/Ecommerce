package com.prod.batch.DTO;

import com.prod.facades.DTO.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDataDTO {
    private int id;
    private String label;
    private String description;
    private String category;
    private String season;
    private List<ImageDTO> images;
}
