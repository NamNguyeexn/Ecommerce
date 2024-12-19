package com.prod.models.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "label_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Label_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int label_id;
    private int product_id;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
