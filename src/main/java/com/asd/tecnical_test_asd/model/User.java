package com.asd.tecnical_test_asd.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @Column(name="id", updatable = false, nullable = false)
    @SequenceGenerator(initialValue = 1, name="idGenUser", sequenceName = "userSEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenUser")
    private Long id;
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
