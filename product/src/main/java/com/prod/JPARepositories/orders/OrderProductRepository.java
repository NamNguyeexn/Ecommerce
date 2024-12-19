package com.prod.JPARepositories.orders;

import com.prod.models.orders.Order_Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface OrderProductRepository extends JpaRepository<Order_Product, Integer>,
        JpaSpecificationExecutor<Order_Product> {
    interface Specs {
        static Specification<Order_Product> byOrderId(int orderId) {
            return (root, query, cb) -> cb.equal(root.get("order_id"), orderId);
        }
        static Specification<Order_Product> byCartProductId(int cartProductId) {
            return (root, query, cb)
                    -> cb.equal(root.get("cart_product_id"), cartProductId);
        }
    }
}
