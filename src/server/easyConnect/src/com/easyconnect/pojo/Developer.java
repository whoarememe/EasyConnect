package com.easyconnect.pojo;


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

@Entity
@Table(name = "Developer", schema = "dbo", catalog = "EasyConnect")
public class Developer implements java.io.Serializable {

	private Integer id;
	private Integer manufacturerId;
	private String password;
	private String phone;
	private String name;
	private Integer state;
	private Set<DeveloperDevice> developerDevices = new HashSet<DeveloperDevice>(0);

	/** default constructor */
	public Developer() {
	}

	/** minimal constructor */
	public Developer(Integer manufacturerId, String password, String phone,
			String name) {
		this.manufacturerId = manufacturerId;
		this.password = password;
		this.phone = phone;
		this.name = name;
	}

	/** full constructor */
	public Developer(Integer manufacturerId, String password, String phone,
			String name, Integer state, Set<DeveloperDevice> developerDevices) {
		this.manufacturerId = manufacturerId;
		this.password = password;
		this.phone = phone;
		this.name = name;
		this.state = state;
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

	@Column(name = "manufacturer_id", nullable = false)
	public Integer getManufacturerId() {
		return this.manufacturerId;
	}

	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phone", nullable = false)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "developer")
	public Set<DeveloperDevice> getDeveloperDevices() {
		return this.developerDevices;
	}

	public void setDeveloperDevices(Set<DeveloperDevice> developerDevices) {
		this.developerDevices = developerDevices;
	}

}