package com.infor.repositories;

import com.infor.models.Address;
import com.infor.models.City;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface CityRepository extends JpaRepository<City, Integer>, JpaSpecificationExecutor<City> {
    interface Specs{
        static Specification<City> byName(String name){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("name"), name);
        }
        static Specification<City> byCityId(int city_id){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("id"), city_id);
        }
    }
}
