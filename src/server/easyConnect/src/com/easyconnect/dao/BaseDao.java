package com.easyconnect.dao;

import java.util.List;

public interface BaseDao {
	
	public void add(Object o);

	public void update(Object o);
	
	public void merge(Object o);

	public void delete(Object o);
	
	public int bulkUpdate(String hql);

	public <T> void deleteByEmpNo(Class<T> clazz, String empNo);

	public <T> List<T> getAll(T t, String order);
	
	public <T> T getOneById(int id, T t);
	
	public <T> List<T> searchByPage( Class<T> clazz , String hql, int currentRecordIndex, int MaxRecords);
	
	public int count(String hql);

	public <T> List<T> searchByHql(Class<T> clazz, String hql, Object[] values);
	
	public void changeTerm();
}
