package com.prod.JPARepositories.details;

import com.prod.models.details.Season;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SeasonRepository extends JpaRepository<Season, Integer>,
        JpaSpecificationExecutor<Season> {
    interface Specs {
        static Specification<Season> byName(String name) {
            return (root, query, cb)
                    -> cb.equal(root.get("name"), name);
        }
        static Specification<Season> byYear(String name) {
            return (root, query, cb)
                    -> cb.equal(root.get("year"), name);
        }
    }
}
