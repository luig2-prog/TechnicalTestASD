package com.asd.tecnical_test_asd.dto;

import com.asd.tecnical_test_asd.model.Area;
import com.asd.tecnical_test_asd.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class FixedAssetDTO {
    private Long id;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z .áéíóúüàè\\-_\\xF1\\xD1]{1,50}", message = "No ingresó un dato valido")
    private String assetName;//    nombre
    private String description;//descripción,
    private String assetType;//tipo,
    private String assetSerial;// serial,
    private int inventoryNumber;// numero interno de inventario,
    private int weight;// peso,
    private int height;// alto,
    private int assetWith;// ancho,
    private int large;// largo,
    private int buyValue;// valor compra,
    private Date dateBuy;// fecha de compra.
    private User user;
    private Area area;
}
