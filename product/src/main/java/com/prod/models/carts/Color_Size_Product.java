package com.prod.models.carts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "color_size_product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Color_Size_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int size_id;
    private int color_id;
    private int product_id;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
