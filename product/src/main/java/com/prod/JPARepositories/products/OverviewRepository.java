package com.prod.JPARepositories.products;

import com.prod.models.products.Overview;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface OverviewRepository extends JpaRepository<Overview, Integer>,
        JpaSpecificationExecutor<Overview> {
    interface Specs {
        /**
         * Hàm truy xuất overview theo product id
         * */
        static Specification<Overview> byProductId(int productId) {
            return (root, query, cb)
                    -> cb.equal(root.get("product_id"), productId);
        }
        /**
         * Hàm truy xuất Overview theo user_id
         * */
        static Specification<Overview> byUserId(int user_id) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("user_id"), user_id);
        }
    }
}
