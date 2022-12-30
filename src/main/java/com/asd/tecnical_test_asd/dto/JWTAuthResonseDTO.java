package com.asd.tecnical_test_asd.dto;

import lombok.Data;

@Data
public class JWTAuthResonseDTO {
    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

}
