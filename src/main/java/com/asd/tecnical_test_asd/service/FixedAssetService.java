package com.asd.tecnical_test_asd.service;


import com.asd.tecnical_test_asd.dto.FixedAssetDTO;
import com.asd.tecnical_test_asd.dto.TypeDateOrSerialDTO;

import java.util.List;

public interface FixedAssetService {
    List<FixedAssetDTO> getFixedAssets();
    FixedAssetDTO save(FixedAssetDTO fixedAssetDTO);
    List<FixedAssetDTO> getByTypeDateOrSerial(TypeDateOrSerialDTO typeDateOrSerialDTO);
}
