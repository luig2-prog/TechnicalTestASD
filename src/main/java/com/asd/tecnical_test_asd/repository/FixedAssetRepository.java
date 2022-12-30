package com.asd.tecnical_test_asd.repository;

import com.asd.tecnical_test_asd.model.FixedAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FixedAssetRepository extends JpaRepository<FixedAsset, Long> {
    /**
     * @description función encargada de hacer la consulta personalizada a la base de datos
     * Se busca en base de datos los arctivos por tipo, fecha de compra o serial
     * @author Luis Hernandez
     * @date(26/12/2022)
     * @param assetType
     * @param dateBuy
     * @param assetSerial
     * @return List<FixedAsset>
     */
    @Query("select f from FixedAsset f where f.assetType = :assetType or f.dateBuy = :dateBuy or f.assetSerial = :assetSerial")
    List<FixedAsset> getByTypeDateOrSerial(
            @Param("assetType") String assetType,
            @Param("dateBuy") Date dateBuy,
            @Param("assetSerial") String assetSerial
    );
}
