package com.prod.JPARepositories.carts;

import com.prod.models.carts.Color_Size_Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ColorSizeProductRepository extends JpaRepository<Color_Size_Product, Integer>,
        JpaSpecificationExecutor<Color_Size_Product> {
    interface Specs {
        static Specification<Color_Size_Product> bySizeId(int size_id) {
            return (root, query, cb) -> cb.equal(root.get("size_id"), size_id);
        }
        static Specification<Color_Size_Product> byProductId(int product_id) {
            return (root, query, cb) -> cb.equal(root.get("product_id"), product_id);
        }
        static Specification<Color_Size_Product> byColorId(int color_id) {
            return (root, query, cb) -> cb.equal(root.get("color_id"), color_id);
        }
        static Specification<Color_Size_Product> byColorIdAndSizeId(int cId, int sId) {
            return Specification.where(bySizeId(sId)).and(byColorId(cId));
        }
    }
}
