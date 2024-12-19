package com.iden.repositories;

import com.iden.models.Account;
import com.iden.models.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface IRoleRepository extends JpaRepository<Role, Integer>{
    Optional<Role> findByName(String name);
}
