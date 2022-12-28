package com.asd.tecnical_test_asd.controller;

import com.asd.tecnical_test_asd.dto.RestResponse;
import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.service.UserService;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger loggerFactory = LoggerFactory.getLogger(UserController.class);
    private RestResponse restResponse;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<RestResponse> getUsers() {
        List<UserDTO> userDTOS = userService.getUsers();
        restResponse = new RestResponse(
            HttpStatus.OK.value(),
            MessagesHandler.RECORDS_LISTED,
            userDTOS
        );
        loggerFactory.info("Found users");
        return ResponseEntity.ok(restResponse);
    }

    @PostMapping
    public ResponseEntity<RestResponse> saveUser(@Valid @RequestBody UserDTO userDTO, Errors errors) {
        if(errors.hasErrors()) {
            loggerFactory.info(MessagesHandler.VALIDATION_ERROR_MESSAGE, errors.getAllErrors());
            restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), MessagesHandler.RECORDS_NOT_STORED, null);
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    MessagesHandler.VALIDATION_ERROR_REQUEST_MESSAGE,
                    new Throwable(MessagesHandler.VALIDATION_ERROR_MESSAGE)
            );
        }
        UserDTO userDTOSaved = userService.save(userDTO);
        restResponse = new RestResponse(
            HttpStatus.OK.value(),
            MessagesHandler.RECORDS_STORED,
            userDTOSaved
        );
        loggerFactory.info("Saved user: {}", userDTO.toString());
        return ResponseEntity.ok(restResponse);
    }

}
