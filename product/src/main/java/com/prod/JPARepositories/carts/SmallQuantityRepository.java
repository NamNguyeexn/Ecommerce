package com.prod.JPARepositories.carts;

import com.prod.models.carts.Small_Quantity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SmallQuantityRepository extends JpaRepository<Small_Quantity, Integer>,
        JpaSpecificationExecutor<Small_Quantity> {
    interface Specs {
        static Specification<Small_Quantity> byCSPId(int cspId) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("color_size_product_id"), cspId);
        }

        static Specification<Small_Quantity> byQuantityId(int quantityId) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("quantity_id"), quantityId);
        }

        static Specification<Small_Quantity> byQuantityGreaterThanQuantity(int quantity) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThan(root.get("quantity"), quantity);
        }

        static Specification<Small_Quantity> byQuantityLessThanQuantity(int quantity) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThan(root.get("quantity"), quantity);
        }

        static Specification<Small_Quantity> byQuantityInRangeQuantity(int min, int max) {
            return Specification.where(byQuantityGreaterThanQuantity(min).and(byQuantityLessThanQuantity(max)));
        }

        static Specification<Small_Quantity> byQuantityGreaterThanSold(int min) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThan(root.get("sold"), min);
        }

        static Specification<Small_Quantity> byQuantityLessThanSold(int max) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThan(root.get("sold"), max);
        }

        static Specification<Small_Quantity> byQuantityInRangeSold(int min, int max) {
            return Specification.where(byQuantityGreaterThanSold(min).and(byQuantityLessThanSold(max)));
        }
    }
}
