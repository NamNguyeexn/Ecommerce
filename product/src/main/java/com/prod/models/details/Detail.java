package com.prod.models.details;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "product_id")
    private int product_id;
    @Column(name = "category_id")
    private int category_id;
    @Column(name = "quantity_id")
    private int quantity_id;
    private String code;
    @Column(name = "season_id")
    private int season_id;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
