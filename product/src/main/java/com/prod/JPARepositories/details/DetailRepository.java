package com.prod.JPARepositories.details;

import com.prod.models.details.Detail;
import com.prod.models.products.Label_Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface DetailRepository extends JpaRepository<Detail, Integer>,
        JpaSpecificationExecutor<Detail> {

    interface Specs {
        static Specification<Detail> byProductId(int product_id) {
            return (root, query, cb) -> cb.equal(root.get("product_id"), product_id);
        }

        static Specification<Detail> byCategoryId(int category_id) {
            return (root, query, cb) -> cb.equal(root.get("category_id"), category_id);
        }

        static Specification<Detail> bySeasonId(int season_id) {
            return (root, query, cb) -> cb.equal(root.get("season_id"), season_id);
        }

        static Specification<Detail> byQuantityId(int quantity_id) {
            return (root, query, cb) -> cb.equal(root.get("quantity_id"), quantity_id);
        }

        static Specification<Detail> byCode(String code) {
            return (root, query, cb) -> cb.equal(root.get("code"), code);
        }
        static Specification<Detail> byCategoryIdAndSeasonId(int category_id, int season_id) {
            return Specification.where(byCategoryId(category_id)).and(bySeasonId(season_id));
        }
        static Specification<Detail> byLabelsId(List<Integer> label_product_ids) {
            return (root, query, criteriaBuilder) ->
                    root.get("product_id").in(label_product_ids);
        }
        static Specification<Detail> byLabelsAndCategoryId(List<Integer> labels, int category_id) {
            return Specification.where(byLabelsId(labels)).and(byCategoryId(category_id));
        }
        static Specification<Detail> byLabelsAndSeasonId(List<Integer> labels, int season_id) {
            return Specification.where(byLabelsId(labels)).and(bySeasonId(season_id));
        }
        static Specification<Detail> byLabelsAndSeasonIdAndCategoryId(List<Integer> labels, int season_id, int category_id) {
            return Specification.where(byLabelsId(labels)).and(byCategoryIdAndSeasonId(category_id, season_id));
        }
    }
}
