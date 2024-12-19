package com.prod.JPARepositories.products;

import com.prod.models.products.Sale_Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SaleProductRepository extends JpaRepository<Sale_Product, Integer>,
        JpaSpecificationExecutor<Sale_Product> {
    interface Specs{
        static Specification<Sale_Product> byProductId(int productId) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("product_id"), productId);
        }
        static Specification<Sale_Product> bySaleId(int saleId) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("sale_id"), saleId);
        }
    }
}
