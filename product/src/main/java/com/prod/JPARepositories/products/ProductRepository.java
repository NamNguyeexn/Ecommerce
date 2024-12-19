package com.prod.JPARepositories.products;

import com.prod.models.details.Category;
import com.prod.models.details.Season;
import com.prod.models.products.Label;
import com.prod.models.products.Label_Product;
import com.prod.models.products.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Integer>,
        JpaSpecificationExecutor<Product> {
    interface Specs {
        /**
         * Hàm truy xuất product theo title
         */
        static Specification<Product> byTitle(String title) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("title"), title);
        }

        static Specification<Product> byKeyLike(String key) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("signature"), "%" + key + "%");
        }

        static Specification<Product> byKey(Set<String> key) {
            return (root, query, criteriaBuilder)
                    -> {
                List<Predicate> predicates = new ArrayList<>();
                for (String s : key) {
                    predicates.add(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get("signature")), "%" + s.toLowerCase() + "%")
                    );
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };
        }

        static Specification<Product> byTitleLike(String key) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + key.toLowerCase() + "%");
        }

        static Specification<Product> byDesLike(String key) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + key.toLowerCase() + "%");
        }

        /**
         * Hàm truy xuất product theo score lớn hơn {}
         */
        static Specification<Product> byScoreGreater(double min) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThanOrEqualTo(root.get("score"), min);
        }

        /**
         * Hàm truy xuất product theo score nhỏ hơn {}
         */
        static Specification<Product> byScoreLess(double max) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThan(root.get("score"), max);
        }

        /**
         * Hàm truy xuất product theo score trong khoảng
         */
        static Specification<Product> byScoreInRange(double min, double max) {
            return Specification.where(byScoreGreater(min)).and(byScoreLess(max));
        }

        /**
         * Hàm truy xuất product theo price lớn hơn {}
         */
        static Specification<Product> byPriceGreater(long min) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThan(root.get("price"), min);
        }

        /**
         * Hàm truy xuất product theo price nhỏ hơn {}
         */
        static Specification<Product> byPriceLess(long max) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThan(root.get("price"), max);
        }

        /**
         * Hàm truy xuất product theo price trong khoảng
         */
        static Specification<Product> byPriceInRange(long min, long max) {
            return Specification.where(byPriceGreater(min)).and(byPriceLess(max));
        }

        static Specification<Product> bySaleId(int saleId) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("sale_id"), saleId);
        }

        static Specification<Product> byScore(double score) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("score"), score);
        }

        static Specification<Product> byScoreAndByTitle(String title, double score) {
            return Specification.where(byScore(score).and(byTitle(title)));
        }

        static Specification<Product> byPriceInRangeAndByTitle(long priceMin, long priceMax, String title) {
            return Specification.where(byPriceInRange(priceMin, priceMax).and(byTitle(title)));
        }
    }
}
