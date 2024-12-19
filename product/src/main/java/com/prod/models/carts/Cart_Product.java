package com.prod.models.carts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cart_products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int color_size_product_id;
    private int cart_id;
    private int quantity;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
