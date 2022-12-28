package com.asd.tecnical_test_asd.repository;

import com.asd.tecnical_test_asd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findUserByUserName();
//    @Query("select u from User u left join fetch u.role where u.username = :username")
//    User findByUsername(@Param("username") String username);
    User findByUsername(String username);

}
