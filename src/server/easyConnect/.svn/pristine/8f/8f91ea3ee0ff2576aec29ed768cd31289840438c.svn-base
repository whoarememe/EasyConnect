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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DeveloperDevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DeveloperDevice", schema = "dbo", catalog = "EasyConnect")
public class DeveloperDevice implements java.io.Serializable {

	// Fields

	private Integer id;
	private DeviceType deviceType;
	private Fucntion fucntion;
	private Developer developer;
	private String model;
	private Integer state;
	private String keyWord;
	private String description;
	private Set<DeviceInUsing> deviceInUsings = new HashSet<DeviceInUsing>(0);

	// Constructors

	/** default constructor */
	public DeveloperDevice() {
	}

	/** minimal constructor */
	public DeveloperDevice(DeviceType deviceType, Fucntion fucntion,
			Developer developer, String model, Integer state, String description) {
		this.deviceType = deviceType;
		this.fucntion = fucntion;
		this.developer = developer;
		this.model = model;
		this.state = state;
		this.description = description;
	}

	/** full constructor */
	public DeveloperDevice(DeviceType deviceType, Fucntion fucntion,
			Developer developer, String model, Integer state, String keyWord,
			String description, Set<DeviceInUsing> deviceInUsings) {
		this.deviceType = deviceType;
		this.fucntion = fucntion;
		this.developer = developer;
		this.model = model;
		this.state = state;
		this.keyWord = keyWord;
		this.description = description;
		this.deviceInUsings = deviceInUsings;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id", nullable = false)
	public DeviceType getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fucntion_id", nullable = false)
	public Fucntion getFucntion() {
		return this.fucntion;
	}

	public void setFucntion(Fucntion fucntion) {
		this.fucntion = fucntion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "developer_id", nullable = false)
	public Developer getDeveloper() {
		return this.developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	@Column(name = "model", nullable = false)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "state", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "key_word")
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "developerDevice")
	public Set<DeviceInUsing> getDeviceInUsings() {
		return this.deviceInUsings;
	}

	public void setDeviceInUsings(Set<DeviceInUsing> deviceInUsings) {
		this.deviceInUsings = deviceInUsings;
	}

}