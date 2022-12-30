package com.asd.tecnical_test_asd.dto;

import com.asd.tecnical_test_asd.model.Area;
import com.asd.tecnical_test_asd.model.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String documentType;
    private Long document;
    private int state;
    private Role role;
    @NotNull
    private Area area;
}
