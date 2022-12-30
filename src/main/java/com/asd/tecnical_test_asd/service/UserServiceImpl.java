package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.model.User;
import com.asd.tecnical_test_asd.repository.AreaRepository;
import com.asd.tecnical_test_asd.repository.RoleRepository;
import com.asd.tecnical_test_asd.repository.UserRepository;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AreaRepository areaRepository;

    /**
     * @description función de servicio encargada para comunicarse con la capa de
     * repositorio y obtener todos los usuarios
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @return List<UserDTO>
     */
    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new CustomException(
                    HttpStatus.NOT_FOUND,
                    MessagesHandler.RECORDS_NOT_LISTED,
                    new Throwable("Exception custom")
            );
        }
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * @description función de servicio encargada para comunicarse con la capa de
     * repositorio y almacenar o actualizar un usuario
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @param userDTO
     * @return UserDTO
     */
    @Override
    public UserDTO save(UserDTO userDTO) {
        User userFind = userRepository.findByUsername(userDTO.getUsername());
        if(userFind != null && userDTO.getId() != userFind.getId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    MessagesHandler.RECORDS_NOT_STORED_USER_EXIST,
                    new Throwable(MessagesHandler.RECORDS_NOT_STORED_USER_EXIST)
            );
        }
        if(!roleRepository.findById(userDTO.getRole().getId()).isPresent()) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    "Tiene que seleccionar un rol existente",
                    new Throwable("Custom Exception")
            );
        }
        logger.info("AREA::: ", userDTO.getArea());
        if(userDTO.getArea() != null &&!areaRepository.findById(userDTO.getArea().getId()).isPresent()) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    "Tiene que seleccionar un area existente",
                    new Throwable("Custom Exception")
            );
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashedPassword);
        User user = modelMapper.map(userDTO, User.class);
        User userSave = userRepository.save(user);
        logger.info("userSave: {} - {} - {}", userSave, userSave.getUsername(), user.getUsername());
        if(userSave == null) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    MessagesHandler.RECORDS_NOT_STORED,
                    new Throwable("No se pudo almacenar el usuario")
            );
        }
        return modelMapper.map(userSave, UserDTO.class);
    }

    @Override
    public UserDTO loggin(String username, String password) {
        User user = userRepository.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(user == null) {
            // Por seguridad no se deben mostrar este mensaje, debe dar un mensaje generico
            // Validar
            throw new CustomException(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado",
                    new Throwable("Custom Exception")
            );
        }
        logger.info("User: {} - {}", user.getUsername(), user.getPassword());
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(HttpStatus.FORBIDDEN, "Credenciales incorrectas");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = modelMapper.map(user, UserDTO.class);
        }
        return userDTO;
    }

}
