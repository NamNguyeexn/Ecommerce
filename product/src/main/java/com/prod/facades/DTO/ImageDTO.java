package com.prod.facades.DTO;

import com.prod.models.ENUM.Type_Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private String url;
    private String name;
    private Type_Image typeImage;
}
