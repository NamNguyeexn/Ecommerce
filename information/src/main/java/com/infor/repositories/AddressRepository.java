package com.infor.repositories;

import com.infor.models.Address;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>, JpaSpecificationExecutor<Address> {
    interface Specs{
        static Specification<Address> byCityId(int city_id){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("city_id"), city_id);
        }
        static Specification<Address> byDistrictId(int district_id){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("district_id"), district_id);
        }
        static Specification<Address> byWardId(int ward_id){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("ward_id"), ward_id);
        }
        static Specification<Address> byStreet(String street){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("street"), street);
        }
        static Specification<Address> byUserId(int user_id){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("user_id"), user_id);
        }
    }
}
