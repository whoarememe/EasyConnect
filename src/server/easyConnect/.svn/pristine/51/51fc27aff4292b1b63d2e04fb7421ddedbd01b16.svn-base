package com.easyconnect.pojo;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Test entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Test", schema = "dbo", catalog = "EasyConnect")
public class Test implements java.io.Serializable {

	// Fields

	private Integer testId;
	private String name;

	// Constructors

	/** default constructor */
	public Test() {
	}

	/** full constructor */
	public Test(String name) {
		this.name = name;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "test_id", unique = true, nullable = false)
	public Integer getTestId() {
		return this.testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}