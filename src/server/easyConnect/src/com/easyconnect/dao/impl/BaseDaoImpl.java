
package com.easyconnect.dao.impl;

import com.easyconnect.dao.BaseDao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
	/**
	 * @param sessionFactory
	 */
	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public void add(Object o) {
		this.getHibernateTemplate().save(o);
	}
	
	public void merge(Object o)
	{
		this.getHibernateTemplate().merge(o);
	}

	public void update(Object o) {
		this.getHibernateTemplate().update(o);
		this.getHibernateTemplate().flush();
	}

	public int bulkUpdate(String hql){
		return this.getHibernateTemplate().bulkUpdate(hql);
	}
	
	public void delete(Object o) {
		this.getHibernateTemplate().delete(o);
		this.getHibernateTemplate().flush();
	}
	
	public Object getById(Object o, int id) {
		return this.getHibernateTemplate().get(o.getClass(), id);
	}

	public <T extends Object> List<T> getByEmpNo(String empNo, T t) {
		String hql = "from " + t.getClass().getName() + "where empNo=?";
		List<T> tList = (List<T>) this.getHibernateTemplate().find(hql, empNo);
		return tList;
	}

	@Override
	public <T extends Object> List<T> getAll(T t, String order) {
		String hql = "from " + t.getClass().getName() + " order by " + (StringUtils.isBlank(order) ? "" : ("" + order));
		List<T> tList = (List<T>) this.getHibernateTemplate().find(hql);
		return tList;
	}

	/**
	 * 
	 * @param arg
	 * @param criterions
	 * @param offset
	 * @param length
	 * @return List
	 */
	protected List getListByCriterionInPage(final Class arg, final Criterion[] criterions, final int offset, final int length) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(arg);

				for (int i = 0; i < criterions.length; i++) {
					criteria.add(criterions[i]);
				}
				if (offset >= 0)
					criteria.setFirstResult(offset);
				if (length >= 0)
					criteria.setMaxResults(length);
				return criteria.list();
			}
		});
		return list;
	}

	/**
	 * 
	 * @param clz
	 * @return
	 */
	public Integer getTotalCountByCriterion(final Class clz, final Criterion[] criterions) {
		Integer result = null;
		result = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(clz.getName());
				c.setProjection(Projections.rowCount());
				for (int i = 0; i < criterions.length; i++) {
					c.add(criterions[i]);
				}
				return Integer.parseInt(c.uniqueResult().toString());
			}

		});
		return result;
	}

	/**
	 * 
	 * @param hql
	 * @return
	 */
	public int getTotalCountByHql(String hql) {
		Iterator i = this.getHibernateTemplate().find(hql).listIterator();
		if (i.hasNext()) {
			Long count = (Long) i.next();
			return count.intValue();

		}
		return 0;
	}

	@Override
	public <T> void deleteByEmpNo(Class<T> clazz, String empNo) {
		String hql = "select t1 from " + clazz.getName() + " t1, PersonBaseInfo t2 where t2.empNo = ? and t1.baseinfo=t2 ";
		List<T> list = this.getHibernateTemplate().find(hql, empNo);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				this.delete(list.get(i));
			}
		}
	}

	@Override
	public <T> T getOneById(int id, T t) {
		// TODO Auto-generated method stub
		return (T) this.getHibernateTemplate().get(t.getClass(), id);
	}

	@Override
	public <T> List<T> searchByPage(Class<T> clazz, String hql,
			int currentRecordIndex, int MaxRecords) {
		SessionFactory sf = this.getHibernateTemplate().getSessionFactory();
		Session session = sf.openSession();
		List<T> list = new LinkedList<T>();
		try {
			Query query = session.createQuery(hql);
			query.setFirstResult(currentRecordIndex);
			query.setMaxResults(MaxRecords);
			list = query.list();
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			try{
				session.close();
			}catch(HibernateException ex){
				
			}
		}
		return list;
	}

	@Override
	public int count(String hql) {
		SessionFactory sf = this.getHibernateTemplate().getSessionFactory();
		Session session = sf.openSession();
		int a = 0;
		try {
			Long res = (Long) session.createQuery(hql).uniqueResult();
			a = res.intValue();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try{
				session.close();
			}catch(HibernateException ex2){
				
			}
		}
		return a;
	}

	@Override
	public <T> List<T> searchByHql(Class<T> clazz,String hql,Object... values){
		return  this.getHibernateTemplate().find(hql, values);
	}
	// update with sql example
	// public void updateAnswerResult(final int questionId, final char
	// rightAnswer) {
	// getHibernateTemplate().execute(new HibernateCallback() {
	// public Object doInHibernate(Session session)
	// throws HibernateException, SQLException {
	// final String hql1 =
	// "update ChoiceAnswer set result = true where questionId = ? and answer = ?";
	// Query query = session.createQuery(hql1);
	// query.setInteger(0, questionId);
	// query.setCharacter(0, rightAnswer);
	// query.executeUpdate();
	// }
	// });
	// }

	@Override
	public void changeTerm() {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		String hql = "UPDATE PeerEvaluation SET isCurrent = 0 WHERE isCurrent = 1";      
		Query query=session.createQuery(hql);  
		query.executeUpdate();
		
		hql = "UPDATE ChooseClass SET isCurrent = 0 WHERE isCurrent = 1";
		query=session.createQuery(hql);  
		query.executeUpdate();
		hql = "UPDATE WinPrize SET isCurrent = 0 WHERE isCurrent = 1";
		query=session.createQuery(hql);  
		query.executeUpdate();
		hql = "UPDATE WinScore SET isCurrent = 0 WHERE isCurrent = 1";
		query=session.createQuery(hql);  
		query.executeUpdate();


	}
//	public static void main(String[] args) {
//		BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
//		baseDaoImpl.changeTerm();
//	}

	// search by sql in page
	// public List<NewsBean> findSpecificNews(int type, int office, int offset,
	// int pageSize) {
	// String hql =
	// "from NewsBean n where n.type=:type and n.office=:office order by n.top desc, n.editDate desc";
	// Session session = getSession();
	// Query query = session.createQuery(hql);
	// query.setParameter("type", type);
	// query.setParameter("office", office);
	// query.setFirstResult(offset);// 定义从第几条查询
	// query.setMaxResults(pageSize);// 定义返回的记录数
	// List<NewsBean> list = query.list();
	// return list;
	// }

}
