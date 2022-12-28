package com.asd.tecnical_test_asd.repository;

import com.asd.tecnical_test_asd.model.FixedAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FixedAssetRepository extends JpaRepository<FixedAsset, Long> {
    @Query("select f from FixedAsset f where f.assetType = :assetType or f.dateBuy = :dateBuy or f.assetType = :assetSerial")
    List<FixedAsset> getByTypeDateOrSerial(
            @Param("assetType") String assetType,
            @Param("dateBuy") Date dateBuy,
            @Param("assetSerial") String assetSerial
    );
}
