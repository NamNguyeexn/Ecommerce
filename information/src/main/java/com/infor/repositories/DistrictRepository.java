package com.infor.repositories;

import com.infor.models.District;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface DistrictRepository extends JpaRepository<District, Integer>, JpaSpecificationExecutor<District> {
    interface Specs{
        static Specification<District> byName(String name){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("name"), name);
        }
        static Specification<District> byCityId(int cityId){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("city_id"), cityId);
        }
    }
}
