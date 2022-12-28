package com.asd.tecnical_test_asd.dto;

import com.asd.tecnical_test_asd.model.Role;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private Role role;
}
