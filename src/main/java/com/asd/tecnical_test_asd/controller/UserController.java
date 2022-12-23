package com.asd.tecnical_test_asd.controller;

import com.asd.tecnical_test_asd.dto.RestResponse;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger loggerFactory = LoggerFactory.getLogger(UserController.class);
    private RestResponse restResponse;

    @GetMapping
    public ResponseEntity<RestResponse> getUsers() {
        restResponse = new RestResponse(HttpStatus.OK.value(),MessagesHandler.RECORDS_LISTED,
                //usuarioDTO
                null);
        return ResponseEntity.ok(restResponse);
    }

}
