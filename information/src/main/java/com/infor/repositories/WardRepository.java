package com.infor.repositories;

import com.infor.models.Ward;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface WardRepository extends JpaRepository<Ward, Integer>, JpaSpecificationExecutor<Ward> {
    interface Specs{
        static Specification<Ward> byName(String name){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("name"), name);
        }
        static Specification<Ward> byDistrictId(int districtId){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("district_id"), districtId);
        }
    }
}
