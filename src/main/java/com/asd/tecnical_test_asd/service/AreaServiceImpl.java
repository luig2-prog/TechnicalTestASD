package com.asd.tecnical_test_asd.service;

import com.asd.tecnical_test_asd.dto.AreaDTO;
import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.exeption.CustomException;
import com.asd.tecnical_test_asd.model.Area;
import com.asd.tecnical_test_asd.model.User;
import com.asd.tecnical_test_asd.repository.AreaRepository;
import com.asd.tecnical_test_asd.utils.MessagesHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AreaDTO> getAreas() {
        List<Area> areas = areaRepository.findAll();
        if(areas.isEmpty()) {
            throw new CustomException(
                    HttpStatus.NOT_FOUND,
                    MessagesHandler.RECORDS_NOT_LISTED,
                    new Throwable("Exception custom")
            );
        }
        return areas.stream().map(area -> modelMapper.map(area, AreaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AreaDTO save(AreaDTO areaDTO) {
        Area areaFind = areaRepository.findByName(areaDTO.getName());
        if(areaFind != null && areaDTO.getId() != areaFind.getId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    MessagesHandler.RECORDS_NOT_STORED_USER_EXIST,
                    new Throwable(MessagesHandler.RECORDS_NOT_STORED_USER_EXIST)
            );
        }
        Area area = modelMapper.map(areaDTO, Area.class);
        Area areaSaved = areaRepository.save(area);
//        logger.info("userSave: {} - {} - {}", areaSaved, areaSaved.getName());
        if(areaSaved == null) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    MessagesHandler.RECORDS_NOT_STORED,
                    new Throwable("No se pudo almacenar el usuario")
            );
        }
        return modelMapper.map(areaSaved, AreaDTO.class);

    }

    @Override
    public AreaDTO findByName(String name) {
        return null;
    }
}
