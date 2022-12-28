package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
//import com.asd.tecnical_test_asd.exeption.ExceptionRestConfig;
import com.asd.tecnical_test_asd.model.User;
import com.asd.tecnical_test_asd.repository.UserRepository;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Override
    public UserDTO save(UserDTO userDTO) {
        logger.info("Todooo: {}" ,usernameExist(userDTO.getUsername()));
        if(usernameExist(userDTO.getUsername())) {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    MessagesHandler.RECORDS_NOT_STORED_USER_EXIST,
                    new Throwable(MessagesHandler.RECORDS_NOT_STORED_USER_EXIST)
            );
        }
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
        return userDTO;
    }

    private boolean usernameExist(String username) {
        boolean exist = false;
        User user = userRepository.findByUsername(username);
        if(user != null) {
            exist = true;
        }
        return exist;
    }

}
