package com.asd.tecnical_test_asd.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TypeDateOrSerialDTO {
    private String assetType;//tipo,
    private Date dateBuy;// fecha de compra.
    private String assetSerial;// serial,
}
