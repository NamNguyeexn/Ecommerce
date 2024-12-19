//package com.prod.JPARepositories;
//
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//
//@Repository
//@EnableJpaRepositories
//public interface TimeRepository<T> extends JpaRepository<T, Integer>,
//        JpaSpecificationExecutor<T> {
//    interface Specs<T> {
//        static <T> Specification<T> getDataCreateBefore(LocalDateTime max) {
//            return (root, query, cb) -> cb.greaterThan(root.get("create_at"), max);
//        }
//        static <T> Specification<T> getDataCreateAfter(LocalDateTime min) {
//            return (root, query, cb) -> cb.lessThan(root.get("create_at"), min);
//        }
//        static <T> Specification<T> getDataUpdateBefore(LocalDateTime max) {
//            return (root, query, cb) -> cb.lessThan(root.get("update_at"), max);
//        }
//        static <T> Specification<T> getDataUpdateAfter(LocalDateTime min) {
//            return (root, query, cb) -> cb.lessThan(root.get("update_at"), min);
//        }
////        static <T> Specification<T> getDataCreateInRange(LocalDateTime max, LocalDateTime min) {
////            return Specification.where((Specification<T>) getDataCreateBefore(max).and(getDataCreateAfter(min)));
////        }
////        static <T> Specification<T> getDataUpdateInRange(LocalDateTime min, LocalDateTime max) {
////            return Specification.where((Specification<T>) getDataUpdateBefore(max).and(getDataUpdateAfter(min)));
////        }
//    }
//}
