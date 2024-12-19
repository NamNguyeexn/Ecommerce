package com.prod.JPARepositories.details;

import com.prod.models.details.Quantity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface QuantityRepository extends JpaRepository<Quantity, Integer>,
        JpaSpecificationExecutor<Quantity> {
    interface Specs {
        /**
         * Hàm truy vấn quantity với quantity lớn hơn {}
         */
        static Specification<Quantity> byQuantitySmallerThan(int quantity) {
            return (root, query, cb) -> cb.lessThan(root.get("quantity"), quantity);
        }

        /**
         * Hàm truy vấn quantity với quantity nhỏ hơn {}
         */
        static Specification<Quantity> byQuantityGreaterThan(int quantity) {
            return (root, query, cb) -> cb.greaterThan(root.get("quantity"), quantity);
        }

        /**
         * Hàm truy vấn quantity với quantity trong khoảng
         */
        static Specification<Quantity> byQuantityInRange(int min, int max) {
            return Specification.where(
                    byQuantityGreaterThan(min)
            ).and(
                    byQuantitySmallerThan(max)
            );
        }

        /**
         * Hàm truy vấn quantity với sold nhỏ hơn {}
         */
        static Specification<Quantity> bySoldSmallerThan(int quantity) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThan(root.get("sold"), quantity);
        }

        /**
         * Hàm truy vấn quantity với sold lớn hơn {}
         */
        static Specification<Quantity> bySoldGreaterThan(int quantity) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThan(root.get("sold"), quantity);
        }

        /**
         * Hàm truy vấn quantity với sold trong khoảng
         * */
        static Specification<Quantity> bySoldInRange(int min, int max) {
            return Specification.where(bySoldGreaterThan(min).and(bySoldSmallerThan(max)));
        }
    }
}
