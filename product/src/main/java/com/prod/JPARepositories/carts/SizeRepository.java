package com.prod.JPARepositories.carts;

import com.prod.models.carts.Size;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface SizeRepository extends JpaRepository<Size, Integer>,
        JpaSpecificationExecutor<Size> {
    interface Specs{
        static Specification<Size> byValue(String value){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("value"), value);
        }
    }
}
