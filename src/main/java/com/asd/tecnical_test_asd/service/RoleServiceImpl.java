package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.dto.RoleDTO;
import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.model.Role;
import com.asd.tecnical_test_asd.model.User;
import com.asd.tecnical_test_asd.repository.RoleRepository;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public RoleDTO findByName(String name) {
        Role role = roleRepository.findByName(name);
        RoleDTO roleDTO = null;
        if (role != null) {
            roleDTO = modelMapper.map(role, RoleDTO.class);
        }
        return roleDTO;
    }

    @Override
    public List<RoleDTO> getRoles() {
        List<Role> roles = roleRepository.findAll();
        if(roles.isEmpty()) {
            throw new CustomException(
                    HttpStatus.NOT_FOUND,
                    MessagesHandler.RECORDS_NOT_LISTED,
                    new Throwable("Exception custom")
            );
        }
        return roles.stream().map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        Role roleFind = roleRepository.findByName(roleDTO.getName());
        if(roleFind != null && roleFind.getId() != roleFind.getId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    "El rol ya existe",
                    new Throwable(MessagesHandler.RECORDS_NOT_STORED_USER_EXIST)
            );
        }
        Role role = modelMapper.map(roleDTO, Role.class);
        Role roleSaved = roleRepository.save(role);
        if(roleSaved == null) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    MessagesHandler.RECORDS_NOT_STORED,
                    new Throwable("No se pudo almacenar el usuario")
            );
        }
        return modelMapper.map(roleSaved, RoleDTO.class);
    }

    /**
     * @description función de servicio encargada para comunicarse con la capa de
     * repositorio y validar el nombre de usuario ya existe en la base de datos
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @param name
     * @return exist
     */
    private boolean roleExist(String name) {
        boolean exist = false;
        Role role = roleRepository.findByName(name);
        if(role != null) {
            exist = true;
        }
        return exist;
    }
}
