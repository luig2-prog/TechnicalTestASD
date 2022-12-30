package com.asd.tecnical_test_asd.config;

import com.asd.tecnical_test_asd.dto.AreaDTO;
import com.asd.tecnical_test_asd.dto.RoleDTO;
import com.asd.tecnical_test_asd.dto.UserDTO;
import com.asd.tecnical_test_asd.model.Area;
import com.asd.tecnical_test_asd.model.Role;
import com.asd.tecnical_test_asd.service.AreaService;
import com.asd.tecnical_test_asd.service.RoleService;
import com.asd.tecnical_test_asd.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private AreaService areaService;

    @Override
    public void run(String...args) throws Exception {
        LOG.info("ROL:::: {}",roleService.findByName("ROLE_USER"));
        if(roleService.findByName("ROLE_USER") == null) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName("ROLE_USER");
            roleService.save(roleDTO);
        }

        if(areaService.findByName("administrativa") == null) {
            AreaDTO areaDTO = new AreaDTO();
            areaDTO.setCity(null);
            areaDTO.setName("administrativa");
            areaService.save(areaDTO);
        }

        if(userService.findByUsername("admin") == null) {
            UserDTO userDTOAdmin = new UserDTO();
            userDTOAdmin.setUsername("admin");
            userDTOAdmin.setPassword("testprueba");
            userDTOAdmin.setAddress("Medellin");
            userDTOAdmin.setEmail("test@gmail.com");
            userDTOAdmin.setState(0);
            userDTOAdmin.setDocument(100310073L);
            userDTOAdmin.setFirstName("Admin");
            userDTOAdmin.setLastName("Admin");
            Role role = new Role();
            role.setName("ROLE_USER");
            role.setId(Long.parseLong("1"));
            userDTOAdmin.setRole(role);
            Area area = new Area();
            area.setCity(null);
            area.setId(Long.parseLong("1"));
            area.setName("administrativa");
            userDTOAdmin.setArea(area);
            userService.save(userDTOAdmin);
        }


    }
}
