package com.asd.tecnical_test_asd.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Person {
    @Id
    @Column(name="id", updatable = false, nullable = false)
    @SequenceGenerator(initialValue = 1, name="idGenPerson", sequenceName = "personSEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenPerson")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Date birthDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDay() {
        return (Date) birthDay.clone();
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = (Date) birthDay.clone();
    }
}
