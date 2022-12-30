package com.asd.tecnical_test_asd.controller;

import com.asd.tecnical_test_asd.dto.*;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.service.AreaService;
import com.asd.tecnical_test_asd.service.JWT;
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
@RequestMapping("/api/v1/area")
public class AreaController {
    private static final Logger loggerFactory = LoggerFactory.getLogger(UserController.class);
    private RestResponse restResponse;

    @Autowired
    private AreaService areaService;
    @Autowired
    private JWT jwt;
    /**
     * @description función encargada de pedir y servir la información de los usuarios registrados en el aplicativo
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @return ResponseEntity
     * @see RestResponse
     */
    @GetMapping
    public ResponseEntity<RestResponse> getAreas() {
        loggerFactory.info("Controller:::::");
        List<AreaDTO> areasDTO = areaService.getAreas();
        restResponse = new RestResponse(
                HttpStatus.OK.value(),
                MessagesHandler.RECORDS_LISTED,
                areasDTO
        );
        loggerFactory.info("Found areas");
        return ResponseEntity.ok(restResponse);
    }

    /**
     * @description función controladora encargada de almacenar y actualizar un usuario del aplicativo
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @param areaDTO
     * @param errors
     * @return ResponseEntity
     * @see RestResponse
     */
    @PostMapping
    public ResponseEntity<RestResponse> saveUser(@Valid @RequestBody AreaDTO areaDTO, Errors errors) {
        if(errors.hasErrors()) {
            loggerFactory.info(MessagesHandler.VALIDATION_ERROR_MESSAGE, errors.getAllErrors());
            restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), MessagesHandler.RECORDS_NOT_STORED, null);
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    MessagesHandler.VALIDATION_ERROR_REQUEST_MESSAGE,
                    new Throwable(MessagesHandler.VALIDATION_ERROR_MESSAGE)
            );
        }
        AreaDTO areaDTOSaved = areaService.save(areaDTO);
        restResponse = new RestResponse(
                HttpStatus.OK.value(),
                MessagesHandler.RECORDS_STORED,
                areaDTOSaved
        );
        loggerFactory.info("Saved user: {}", areaDTOSaved.toString());
        return ResponseEntity.ok(restResponse);
    }

}
