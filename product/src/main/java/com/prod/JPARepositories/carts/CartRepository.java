package com.prod.JPARepositories.carts;

import com.prod.models.carts.Cart;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CartRepository extends JpaRepository<Cart, Integer>,
        JpaSpecificationExecutor<Cart> {
    interface Specs {
        static Specification<Cart> byUserId(int userId) {
            return (root, criteriaQuery, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("user_id"), userId);
        }

        static Specification<Cart> byCode(String code) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("code"), code);
        }
    }
}
