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

	@Enumerated(EnumType.STRING)
	private Roles role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

}
