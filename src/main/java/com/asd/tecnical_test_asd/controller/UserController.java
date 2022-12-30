package com.asd.tecnical_test_asd.controller;

import com.asd.tecnical_test_asd.dto.JWTAuthResonseDTO;
import com.asd.tecnical_test_asd.dto.LoginDTO;
import com.asd.tecnical_test_asd.dto.RestResponse;
import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.service.JWT;
import com.asd.tecnical_test_asd.service.UserService;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger loggerFactory = LoggerFactory.getLogger(UserController.class);
    private RestResponse restResponse;

    @Autowired
    private UserService userService;
    @Autowired
    private JWT jwt;
    @Autowired
    private AuthenticationManager manager;
    /**
     * @description función encargada de pedir y servir la información de los usuarios registrados en el aplicativo
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @return ResponseEntity
     * @see RestResponse
     */
    @GetMapping
    public ResponseEntity<RestResponse> getUsers() {
        loggerFactory.info("Controller:::::");
        List<UserDTO> userDTOS = userService.getUsers();
        restResponse = new RestResponse(
            HttpStatus.OK.value(),
            MessagesHandler.RECORDS_LISTED,
            userDTOS
        );
        loggerFactory.info("Found users");
        return ResponseEntity.ok(restResponse);
    }

    /**
     * @description función controladora encargada de almacenar y actualizar un usuario del aplicativo
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @param userDTO
     * @param errors
     * @return ResponseEntity
     * @see RestResponse
     */
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

    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) throws JsonProcessingException {
        loggerFactory.info("Login: {}", loginDTO.getUsernameOrEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword());
        loggerFactory.info("Login 2: {}", authentication.getPrincipal().toString());

        manager.authenticate(authentication);

        loggerFactory.info("Login 3: {}", authentication.getPrincipal().toString());

        UserDTO userDTO = userService.loggin(loginDTO.getUsernameOrEmail(), loginDTO.getPassword());
        loggerFactory.info("Login 4: {}", authentication.getPrincipal().toString());
        JWTAuthResonseDTO responseToken = new JWTAuthResonseDTO();
        responseToken.setTokenDeAcceso(jwt.getJWTToken(userDTO, request.getRemoteAddr()));
        restResponse = new RestResponse(
                HttpStatus.OK.value(),
                "Usuario validado",
                responseToken
        );
        return ResponseEntity.ok(restResponse);
    }

}
