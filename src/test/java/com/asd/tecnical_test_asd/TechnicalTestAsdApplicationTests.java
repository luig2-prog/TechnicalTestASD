package com.asd.tecnical_test_asd;

import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.model.User;
import com.asd.tecnical_test_asd.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TechnicalTestAsdApplicationTests {

    @Autowired
    private UserService userService;

	@Test
	void contextLoads() {

        UserDTO userDTO = userService.findByUsername("admin");

        if(userDTO == null) {
            Assertions.fail("No existe el usuario administrador");;
        }
        Assertions.assertEquals("admin", userDTO.getUsername());


	}

}
