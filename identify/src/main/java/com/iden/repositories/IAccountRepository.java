package com.iden.repositories;

import com.iden.models.Account;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface IAccountRepository extends JpaRepository<Account, Integer>,
        JpaSpecificationExecutor<Account>{
    interface Specs{
        static Specification<Account> byUsername(String username){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("username"), username);
        }
        static Specification<Account> byEmail(String email){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("email"), email);
        }
        static Specification<Account> byPhone(String phone){
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("phone"), phone);
        }
    }
}
