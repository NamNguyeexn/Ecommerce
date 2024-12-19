package com.prod.facades.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDTO {
    private int id;
    private int season_id;
    private float value;
    private LocalDateTime start;
    private LocalDateTime end;
    @Nullable
    private List<Integer> product_ids;
}
