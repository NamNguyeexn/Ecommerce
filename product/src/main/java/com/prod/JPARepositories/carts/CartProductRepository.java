package com.prod.JPARepositories.carts;

import com.prod.models.carts.Cart_Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CartProductRepository extends JpaRepository<Cart_Product, Integer>,
        JpaSpecificationExecutor<Cart_Product> {
    interface Specs {
        static Specification<Cart_Product> byCartId(int cartId) {
            return (root, query, cb) -> cb.equal(root.get("cart_id"), cartId);
        }
        static Specification<Cart_Product> byColorSizeProductId(int colorSizeProductId) {
            return (root, query, cb) -> cb.equal(root.get("color_size_product_id"), colorSizeProductId);
        }
        static Specification<Cart_Product> byQuantity(int quantity) {
            return (root, query, cb) -> cb.equal(root.get("quantity"), quantity);
        }
    }
}
