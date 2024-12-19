package com.prod.JPARepositories.products;

import com.prod.models.products.Label;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface LabelRepository extends JpaRepository<Label, Integer>,
        JpaSpecificationExecutor<Label> {
    interface Specs {
        static Specification<Label> byName(String name) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("name"), name);
        }
        static Specification<Label> byNameLike(String name) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
        }

        static Specification<Label> byCode(String code) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("code"), code);
        }
    }
}
