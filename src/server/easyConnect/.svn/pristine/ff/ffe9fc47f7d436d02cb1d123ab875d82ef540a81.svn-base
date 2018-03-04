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
 * DeviceType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DeviceType", schema = "dbo", catalog = "EasyConnect")
public class DeviceType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String typeName;
	private String pic;
	private Set<DeveloperDevice> developerDevices = new HashSet<DeveloperDevice>(
			0);

	// Constructors

	/** default constructor */
	public DeviceType() {
	}

	/** minimal constructor */
	public DeviceType(String typeName) {
		this.typeName = typeName;
	}

	/** full constructor */
	public DeviceType(String typeName, String pic,
			Set<DeveloperDevice> developerDevices) {
		this.typeName = typeName;
		this.pic = pic;
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

	@Column(name = "type_name", nullable = false)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "pic")
	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deviceType")
	public Set<DeveloperDevice> getDeveloperDevices() {
		return this.developerDevices;
	}

	public void setDeveloperDevices(Set<DeveloperDevice> developerDevices) {
		this.developerDevices = developerDevices;
	}

}