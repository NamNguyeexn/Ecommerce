package com.prod.JPARepositories.orders;

import com.prod.models.orders.Bill;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>,
        JpaSpecificationExecutor<Bill> {
    interface Specs {
        static Specification<Bill> byOrderId(int orderId) {
            return (root, query, cb) -> cb.equal(root.get("order_id"), orderId);
        }
        static Specification<Bill> byAddressId(int addressId) {
            return (root, query, cb) -> cb.equal(root.get("address_id"), addressId);
        }
        static Specification<Bill> byStatus(String status) {
            return (root, query, cb) -> cb.equal(root.get("status"), status);
        }
    }
}
