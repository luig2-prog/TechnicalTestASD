package com.asd.tecnical_test_asd.repository;

import com.asd.tecnical_test_asd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @description función encargada de hacer la consulta a la base de datos por el nombre de usuario
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @param username
     * @return User
     */
    User findByUsername(String username);

}
