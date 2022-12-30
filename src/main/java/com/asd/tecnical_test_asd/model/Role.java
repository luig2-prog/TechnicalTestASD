package com.asd.tecnical_test_asd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
//@Table(name = "ROLES")
public class Role {

	public enum Roles {
		USER, ADMIN
	}

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@SequenceGenerator(initialValue = 1, name = "idGenRol", sequenceName = "rolSEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenRol")
	private Long id;
	@Column(unique=true)
	private String name;

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
}
