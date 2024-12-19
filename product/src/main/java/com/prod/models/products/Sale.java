package com.prod.models.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int season_id;
    private float value;
    private LocalDateTime start;
    private LocalDateTime end;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
