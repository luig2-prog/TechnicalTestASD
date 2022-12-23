package com.asd.tecnical_test_asd.repository;

import com.asd.tecnical_test_asd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
