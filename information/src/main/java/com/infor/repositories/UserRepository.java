package com.infor.repositories;

import com.infor.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    interface Specs {
        static Specification<User> byIsActive(boolean isActive) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("is_active"), isActive);
        }
        static Specification<User> byPhone(String phone) {
            return (root, query, criteriaBuilder) ->
                    phone != null && !phone.isEmpty()
                            ? criteriaBuilder.like(root.get("phone"), "%" + phone + "%")
                            : null;
//            return (root, query, cb) -> cb.equal(root.get("phone"), phone);
        }
        static Specification<User> byFullName(String full_name) {
            return (root, query, criteriaBuilder) ->
                    full_name != null && !full_name.isEmpty()
                            ? criteriaBuilder.like(root.get("full_name"), "%" + full_name + "%")
                            : null;
//            return (root, query, cb) -> cb.equal(root.get("full_name"), full_name);
        }
        static Specification<User> byAccountId(int account_id) {
            return (root, query, cb) -> cb.equal(root.get("account_id"), account_id);
        }
        static Specification<User> byUserName(String userName) {
            return (root, query, criteriaBuilder) ->
                    userName != null && !userName.isEmpty()
                            ? criteriaBuilder.like(root.get("userName"), "%" + userName + "%")
                            : null;
        }

        static Specification<User> byEmail(String email) {
            return (root, query, criteriaBuilder) ->
                    email != null && !email.isEmpty()
                            ? criteriaBuilder.like(root.get("email"), "%" + email + "%")
                            : null;
        }

        static Specification<User> byKey(String key) {
            return (root, query, criteriaBuilder) -> {
                if (key == null || key.isEmpty()) {
                    return null; // Không áp dụng điều kiện nếu key rỗng
                }
                return criteriaBuilder.or(
                        criteriaBuilder.like(root.get("full_name"), "%" + key + "%"),
                        criteriaBuilder.like(root.get("user_name"), "%" + key + "%"),
                        criteriaBuilder.like(root.get("email"), "%" + key + "%"),
                        criteriaBuilder.like(root.get("phone"), "%" + key + "%")
                );
            };
        }
    }
//    @Query("SELECT u FROM User u " +
//            "WHERE (:fullName IS NULL OR u.full_name LIKE %:fullName%) " +
//            "AND (:userName IS NULL OR u.userName LIKE %:userName%) " +
//            "AND (:email IS NULL OR u.email LIKE %:email%) " +
//            "AND (:phone IS NULL OR u.phone LIKE %:phone%)")
//    Page<User> findAllUsers(
//            @Param("fullName") String fullName,
//            @Param("userName") String userName,
//            @Param("email") String email,
//            @Param("phone") String phone,
//            Pageable pageable
//    );
}
