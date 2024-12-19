package com.prod.JPARepositories.products;

import com.prod.models.products.Label_Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface LabelProductRepository extends JpaRepository<Label_Product, Integer>,
        JpaSpecificationExecutor<Label_Product> {
    interface Specs{
        static Specification<Label_Product> byProductId(int productId){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("product_id"), productId);
        }
        static Specification<Label_Product> byLabelId(int labelId){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("label_id"), labelId);
        }
        static Specification<Label_Product> byLabelIdAndProductId(int lId, int pId){
            return Specification.where(byProductId(pId).and(byLabelId(lId)));
        }
    }
}
