package com.asd.tecnical_test_asd.controller;

import com.asd.tecnical_test_asd.dto.FixedAssetDTO;
import com.asd.tecnical_test_asd.dto.RestResponse;
import com.asd.tecnical_test_asd.dto.TypeDateOrSerialDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.service.FixedAssetService;
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
@RequestMapping("/api/v1/fixed-asset")
public class FixedAssetController {

    private static final Logger loggerFactory = LoggerFactory.getLogger(FixedAssetController.class);
    private RestResponse restResponse;

    @Autowired
    FixedAssetService fixedAssetsService;

    @GetMapping
    public ResponseEntity<RestResponse> getFixedAssets() {
        List<FixedAssetDTO> fixedAssetDTOS = fixedAssetsService.getFixedAssets();
        restResponse = new RestResponse(
                HttpStatus.OK.value(),
                MessagesHandler.RECORDS_LISTED,
                fixedAssetDTOS
        );
        loggerFactory.info("Found users");
        return ResponseEntity.ok(restResponse);
    }

    @PostMapping
    public ResponseEntity<RestResponse> saveFixedAsset(@Valid @RequestBody FixedAssetDTO fixedAssetDTO, Errors errors) {
        if(errors.hasErrors()) {
            loggerFactory.info(MessagesHandler.VALIDATION_ERROR_MESSAGE, errors.getAllErrors());
            restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), MessagesHandler.RECORDS_NOT_STORED, null);
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    MessagesHandler.VALIDATION_ERROR_REQUEST_MESSAGE,
                    new Throwable(MessagesHandler.VALIDATION_ERROR_MESSAGE)
            );
        }
        FixedAssetDTO fixedAssetDTOSaved = fixedAssetsService.save(fixedAssetDTO);
        restResponse = new RestResponse(
                HttpStatus.OK.value(),
                MessagesHandler.RECORDS_STORED,
                fixedAssetDTOSaved
        );
        return ResponseEntity.ok(restResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<RestResponse> getByTypeDateOrSerial(@Valid @RequestBody TypeDateOrSerialDTO typeDateOrSerialDTO, Errors errors) {
        if(errors.hasErrors()) {
            loggerFactory.info(MessagesHandler.VALIDATION_ERROR_MESSAGE, errors.getAllErrors());
            restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), MessagesHandler.RECORDS_NOT_STORED, null);
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    MessagesHandler.VALIDATION_ERROR_REQUEST_MESSAGE,
                    new Throwable(MessagesHandler.VALIDATION_ERROR_MESSAGE)
            );
        }
        List<FixedAssetDTO> fixedAssetDTO = fixedAssetsService.getByTypeDateOrSerial(typeDateOrSerialDTO);
        restResponse = new RestResponse(
                HttpStatus.OK.value(),
                MessagesHandler.RECORDS_LISTED,
                fixedAssetDTO
        );
        return ResponseEntity.ok(restResponse);
    }

}