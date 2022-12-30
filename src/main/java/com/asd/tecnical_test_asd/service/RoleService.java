package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.dto.RoleDTO;
import com.asd.tecnical_test_asd.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(Long id);
    RoleDTO findByName(String name);
    List<RoleDTO> getRoles();
    RoleDTO save(RoleDTO roleDTO);
}
