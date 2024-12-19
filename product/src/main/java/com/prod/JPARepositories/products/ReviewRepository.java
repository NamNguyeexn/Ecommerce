package com.prod.JPARepositories.products;

import com.prod.models.products.Review;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ReviewRepository extends JpaRepository<Review, Integer>,
        JpaSpecificationExecutor<Review> {
    interface Specs {
        /**
         * Hàm truy xuất review theo userid
         */
        static Specification<Review> byUserId(int userId) {
            return (root, query, cb) -> cb.equal(root.get("user_id"), userId);
        }

        /**
         * Hàm truy xuất review theo productid
         */
        static Specification<Review> byProductId(int productId) {
            return (root, query, cb) -> cb.equal(root.get("product_id"), productId);
        }

        /**
         * Hàm truy xuất review theo value
         */
        static Specification<Review> byValue(double value) {
            return (root, query, cb) -> cb.equal(root.get("value"), value);
        }

        /**
         * Hàm truy xuất review lớn hơn {}
         */
        static Specification<Review> byValueGreaterThan(double value) {
            return (root, query, cb) -> cb.greaterThan(root.get("value"), value);
        }

        /**
         * Hàm truy xuất review nhỏ hơn {}
         */
        static Specification<Review> byValueLessThan(double value) {
            return (root, query, cb) -> cb.lessThan(root.get("value"), value);
        }

        /**
         * Hàm truy xuất review trong khoảng
         */
        static Specification<Review> byValueInRange(double min, double max) {
            return Specification.where(byValueGreaterThan(min))
                    .and(byValueLessThan(max));
        }
    }
}
