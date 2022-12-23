package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.model.User;
import com.asd.tecnical_test_asd.repository.UserRepository;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, MessagesHandler.RECORDS_NOT_LISTED, null);
        }
        return null;
    }

}
