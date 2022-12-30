package com.asd.tecnical_test_asd.dto;

import com.asd.tecnical_test_asd.model.City;
import lombok.Data;

@Data
public class AreaDTO {
    private Long id;
    private String name;
    private City city;
}
