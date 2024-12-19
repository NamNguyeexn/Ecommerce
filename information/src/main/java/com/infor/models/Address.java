package com.infor.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    // Quan hệ Many-to-One với City
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;

    // Quan hệ Many-to-One với District
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", referencedColumnName = "id", nullable = false)
    private District district;

    // Quan hệ Many-to-One với Ward
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id", referencedColumnName = "id", nullable = false)
    private Ward ward;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String street;
    private int user_id;
    @Lob
    @Column(columnDefinition = "TEXT")
//    Nguoi nhan hang
    private String  consignee;
    private boolean isDefault;
    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updated_at = LocalDateTime.now();
    @Nullable
    private String phone = "";
}
