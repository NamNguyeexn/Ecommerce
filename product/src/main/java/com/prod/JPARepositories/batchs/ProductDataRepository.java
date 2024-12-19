package com.prod.JPARepositories.batchs;

import com.prod.batch.DTO.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ProductDataRepository extends JpaRepository<ProductData, Integer>,
        JpaSpecificationExecutor<ProductData> {
}
