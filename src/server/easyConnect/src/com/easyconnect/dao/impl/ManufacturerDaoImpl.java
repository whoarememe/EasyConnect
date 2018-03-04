package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.dao.ManufacturerDao;
import com.easyconnect.pojo.Manufacturer;

@Repository
public class ManufacturerDaoImpl extends BaseDaoImpl implements ManufacturerDao {

	@Override
	public Manufacturer getManufacturerById(int manuId) {
		String hql = "from Manufacturer as m where m.id=?";
		List<Manufacturer> manufacturerList = (List<Manufacturer>)this.getHibernateTemplate().find(hql, manuId);
		
		if(null == manufacturerList || 0 == manufacturerList.size())
			return null;
		else
			return manufacturerList.get(0);
	}
	
	@Override
	public List<Manufacturer> getAllManufacturer()
	{
		String hql = "from Manufacturer";
		List<Manufacturer> manufacturerList = (List<Manufacturer>)this.getHibernateTemplate().find(hql);
		
		if(null == manufacturerList || 0 == manufacturerList.size())
			return null;
		else
			return manufacturerList;
	}

}
