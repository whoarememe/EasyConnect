package com.easyconnect.pojo;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Manufacturer entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Manufacturer", schema = "dbo", catalog = "EasyConnect")
public class Manufacturer implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String addr;

	// Constructors

	/** default constructor */
	public Manufacturer() {
	}

	/** full constructor */
	public Manufacturer(String name, String addr) {
		this.name = name;
		this.addr = addr;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "addr", nullable = false)
	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}