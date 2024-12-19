package com.prod.JPARepositories.orders;

import com.prod.models.orders.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Integer>,
        JpaSpecificationExecutor<Order> {
    interface Specs {
        static Specification<Order> byUserId(int userId) {
            return (root, query, cb) -> cb.equal(root.get("user_id"), userId);
        }
        static Specification<Order> byStatus(String status) {
            return (root, query, cb) -> cb.equal(root.get("status"), status);
        }
        static Specification<Order> byOrderDate(LocalDateTime orderDate) {
            return (root, query, cb)
                    -> cb.greaterThan(root.get("order_date"), orderDate);
        }
        static Specification<Order> byOrderTotalGreaterThan(long min){
            return (root, query, cb)
                    -> cb.greaterThan(root.get("total"), min);
        }
        static Specification<Order> byOrderTotalLessThan(long max){
            return  (root, query, cb)
                    -> cb.lessThan(root.get("total"), max);
        }
        static Specification<Order> byOrderInRange(long min, long max){
            return Specification.where(byOrderTotalGreaterThan(min).and(byOrderTotalLessThan(max)));
        }
    }
}
