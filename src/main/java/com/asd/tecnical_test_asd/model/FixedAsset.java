package com.asd.tecnical_test_asd.model;

import javax.persistence.*;
import java.util.Date;
@Entity
public class FixedAsset {
    @Id
    @Column(name="id", updatable = false, nullable = false)
    @SequenceGenerator(initialValue = 1, name="idGenFixedAsset", sequenceName = "fixedAssetSEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenFixedAsset")
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetSerial() {
        return assetSerial;
    }

    public void setAssetSerial(String assetSerial) {
        this.assetSerial = assetSerial;
    }

    public int getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAssetWith() {
        return assetWith;
    }

    public void setAssetWith(int assetWith) {
        this.assetWith = assetWith;
    }

    public int getLarge() {
        return large;
    }

    public void setLarge(int large) {
        this.large = large;
    }

    public float getBuyValue() {
        return buyValue;
    }

    public void setBuyValue(int buyValue) {
        this.buyValue = buyValue;
    }

    public Date getDateBuy() {
        return (Date) dateBuy.clone();
    }

    public void setDateBuy(Date dateBuy) {
        this.dateBuy = (Date) dateBuy.clone();
    }
}
