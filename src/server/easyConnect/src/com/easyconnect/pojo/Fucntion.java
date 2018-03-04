package com.easyconnect.pojo;

// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Fucntion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Fucntion", schema = "dbo", catalog = "EasyConnect")
public class Fucntion implements java.io.Serializable {

	// Fields

	private Integer id;
	private String functions;
	private Set<DeveloperDevice> developerDevices = new HashSet<DeveloperDevice>(
			0);

	// Constructors

	/** default constructor */
	public Fucntion() {
	}

	/** minimal constructor */
	public Fucntion(String functions) {
		this.functions = functions;
	}

	/** full constructor */
	public Fucntion(String functions, Set<DeveloperDevice> developerDevices) {
		this.functions = functions;
		this.developerDevices = developerDevices;
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

	@Column(name = "functions", nullable = false)
	public String getFunctions() {
		return this.functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fucntion")
	public Set<DeveloperDevice> getDeveloperDevices() {
		return this.developerDevices;
	}

	public void setDeveloperDevices(Set<DeveloperDevice> developerDevices) {
		this.developerDevices = developerDevices;
	}

}