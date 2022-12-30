package com.asd.tecnical_test_asd.controller;

import com.asd.tecnical_test_asd.dto.RestResponse;
import com.asd.tecnical_test_asd.dto.RoleDTO;
import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.service.RoleService;
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
@RequestMapping("/api/v1/role")
public class RoleController {

    private static final Logger loggerFactory = LoggerFactory.getLogger(UserController.class);
    private RestResponse restResponse;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<RestResponse> getRoles() {
        List<RoleDTO> rolesDtos = roleService.getRoles();
        restResponse = new RestResponse(
                HttpStatus.OK.value(),
                MessagesHandler.RECORDS_LISTED,
                rolesDtos
        );
        loggerFactory.info("Found users");
        return ResponseEntity.ok(restResponse);
    }

    /**
     * @description función controladora encargada de almacenar y actualizar un usuario del aplicativo
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @param roleDTO
     * @param errors
     * @return ResponseEntity
     * @see RestResponse
     */
    @PostMapping
    public ResponseEntity<RestResponse> saveRole(@Valid @RequestBody RoleDTO roleDTO, Errors errors) {
        if(errors.hasErrors()) {
            loggerFactory.info(MessagesHandler.VALIDATION_ERROR_MESSAGE, errors.getAllErrors());
            restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), MessagesHandler.RECORDS_NOT_STORED, null);
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    MessagesHandler.VALIDATION_ERROR_REQUEST_MESSAGE,
                    new Throwable(MessagesHandler.VALIDATION_ERROR_MESSAGE)
            );
        }
        RoleDTO roleDtoSaved = roleService.save(roleDTO);
        restResponse = new RestResponse(
                HttpStatus.OK.value(),
                MessagesHandler.RECORDS_STORED,
                roleDtoSaved
        );
        loggerFactory.info("Saved user: {}", roleDtoSaved.toString());
        return ResponseEntity.ok(restResponse);
    }
}
