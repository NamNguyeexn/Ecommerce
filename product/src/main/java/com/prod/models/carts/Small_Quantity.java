package com.prod.models.carts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "small_quantities")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Small_Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int color_size_product_id;
    private int quantity_id;
    private int quantity;
    @Builder.Default
    private int sold = 0;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
