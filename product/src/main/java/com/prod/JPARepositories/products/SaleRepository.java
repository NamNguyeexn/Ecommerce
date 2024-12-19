package com.prod.JPARepositories.products;

import com.prod.models.products.Sale;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@EnableJpaRepositories
public interface SaleRepository extends JpaRepository<Sale, Integer>, JpaSpecificationExecutor<Sale> {
    interface Specs{
        static Specification<Sale> bySeasonId(int seasonId){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("season_id"), seasonId);
        }
        static Specification<Sale> byValueGreaterThan(int value){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThan(root.get("value"), value);
        }
        static Specification<Sale> byValueLessThan(int value){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThan(root.get("value"), value);
        }
        static Specification<Sale> byValueInRange(int min, int max){
            return Specification.where(byValueGreaterThan(min).and(byValueLessThan(max)));
        }
        static Specification<Sale> byDateBefore(LocalDateTime date){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThan(root.get("end"), date);
        }
        static Specification<Sale> byDateAfter(LocalDateTime date){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThan(root.get("start"), date);
        }
        static Specification<Sale> byDateBetween(LocalDateTime start, LocalDateTime end){
            return Specification.where(byDateAfter(start).and(byDateBefore(end)));
        }
        static Specification<Sale> byNow(){
            return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get("end"), LocalDateTime.now()));
        }
    }
}
