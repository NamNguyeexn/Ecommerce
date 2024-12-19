package com.prod.JPARepositories.carts;

import com.prod.models.carts.Color;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ColorRepository extends JpaRepository<Color, Integer>
        , JpaSpecificationExecutor<Color> {
    interface Specs{
        static Specification<Color> byValue(String value){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("value"), value);
        }
        static Specification<Color> byCode(String code){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("code"), code);
        }
    }
}
