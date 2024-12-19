package com.prod.JPARepositories.details;

import com.prod.models.details.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Integer>,
        JpaSpecificationExecutor<Category> {
    interface Specs{
        /**
         * Hàm truy xuất category theo code
         * */
        static Specification<Category> byCode(String code){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("code"), code);
        }
        /**
         * Hàm truy xuất category theo name
         * */
        static Specification<Category> byName(String name){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("name"), name);
        }
        static Specification<Category> byNameLike(String name){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("name"), name);
        }
    }
}
