package com.asd.tecnical_test_asd.repository;

import com.asd.tecnical_test_asd.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Area findByName(String name);
}
