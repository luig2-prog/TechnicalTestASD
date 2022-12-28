package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.dto.FixedAssetDTO;
import com.asd.tecnical_test_asd.dto.TypeDateOrSerialDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.model.FixedAsset;
import com.asd.tecnical_test_asd.repository.FixedAssetRepository;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FixedAssetServiceImpl implements FixedAssetService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    FixedAssetRepository fixedAssetRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * @description función de servicio encargada para comunicarse con la capa de
     * repositorio y obtener todos los activos fijos
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @return
     */
    @Override
    public List<FixedAssetDTO> getFixedAssets() {
        List<FixedAsset> fixedAssets = fixedAssetRepository.findAll();
        if(fixedAssets.isEmpty()) {
            throw new CustomException(
                    HttpStatus.NOT_FOUND,
                    MessagesHandler.RECORDS_NOT_LISTED,
                    new Throwable("Exception custom")
            );
        }
        return fixedAssets.stream().map(fixedAsset -> modelMapper.map(fixedAsset, FixedAssetDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * @description función de servicio encargada para comunicarse con la capa de
     * repositorio y almacenar o actualizar un activo fijo
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @param fixedAssetDTO
     * @return
     */
    @Override
    public FixedAssetDTO save(FixedAssetDTO fixedAssetDTO) {
        FixedAsset fixedAsset = modelMapper.map(fixedAssetDTO, FixedAsset.class);
        logger.info("VALIDATION USER: {}", fixedAsset.getUser().getUsername());
        FixedAsset fixedAssetSaved = fixedAssetRepository.save(fixedAsset);
        if(fixedAssetSaved == null) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    MessagesHandler.RECORDS_NOT_STORED,
                    new Throwable("No se pudo almacenar el activo")
            );
        }
        return fixedAssetDTO;
    }

    /**
     * @description función de servicio encargada para comunicarse con la capa de repositorio y
     * obtener todos los activos fijos por tipo, fecha de compa o serial
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @param typeDateOrSerialDTO
     * @return
     */
    @Override
    public List<FixedAssetDTO> getByTypeDateOrSerial(TypeDateOrSerialDTO typeDateOrSerialDTO) {
        List<FixedAsset> fixedAssetSearch = fixedAssetRepository.getByTypeDateOrSerial(
                typeDateOrSerialDTO.getAssetType(),
                typeDateOrSerialDTO.getDateBuy(),
                typeDateOrSerialDTO.getAssetSerial()
        );
        if(fixedAssetSearch == null) {
            throw new CustomException(
                    HttpStatus.NOT_FOUND,
                    MessagesHandler.RECORDS_NOT_LISTED,
                    new Throwable("Exception custom")
            );
        }
        logger.info("ACTIVOS FIJOS: {} - {}", fixedAssetSearch.isEmpty(), fixedAssetSearch.size());
        return fixedAssetSearch.stream().map(fixedAsset -> modelMapper.map(fixedAsset, FixedAssetDTO.class))
                .collect(Collectors.toList());
    }
}
