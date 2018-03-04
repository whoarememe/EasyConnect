package com.easyconnect.dao;

import java.util.List;

import com.easyconnect.pojo.Manufacturer;

public interface ManufacturerDao extends BaseDao {

	public Manufacturer getManufacturerById(int manuId);
	
	public List<Manufacturer> getAllManufacturer();
} 
