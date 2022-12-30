package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO save(UserDTO userDTO);
    UserDTO loggin(String username, String password);
    UserDTO findByUsername(String username);
}
