package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.dto.AreaDTO;
import com.asd.tecnical_test_asd.model.Area;

import java.util.List;

public interface AreaService {
    List<AreaDTO> getAreas();
    AreaDTO save(AreaDTO areaDTO);
    AreaDTO findByName(String name);
}
