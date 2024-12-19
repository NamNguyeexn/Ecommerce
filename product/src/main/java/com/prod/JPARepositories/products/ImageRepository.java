package com.prod.JPARepositories.products;

import com.prod.models.ENUM.Type_Image;
import com.prod.models.products.Image;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>,
        JpaSpecificationExecutor<Image> {
    interface Specs {
        static Specification<Image> byProductId(int productId) {
            return (root, query, cb) -> cb.equal(root.get("product_id"), productId);
        }
        static Specification<Image> byType(Type_Image type) {
            return (root, query, cb) -> cb.equal(root.get("type"), type);
        }
    }
}
