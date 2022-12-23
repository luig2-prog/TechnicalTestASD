package com.asd.tecnical_test_asd.model;

import javax.persistence.*;

@Entity
public class Area {
    @Id
    @Column(name="id", updatable = false, nullable = false)
    @SequenceGenerator(initialValue = 1, name="idGenArea", sequenceName = "areaSEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenArea")
    private Long id;
    private String name;
    private int postalCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
