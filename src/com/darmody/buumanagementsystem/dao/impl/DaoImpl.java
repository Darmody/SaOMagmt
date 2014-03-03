package com.darmody.buumanagementsystem.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.darmody.buumanagementsystem.dao.Dao;

/**
 * 2013.7.17
 * @author Darmody
 * @content 数据库原子操作接口实现类
 */

public abstract class DaoImpl extends HibernateTemplate implements Dao{

	
	@Override
	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		
		super.setSessionFactory(sessionFactory);
	}	

	@Override
	public int create(Object entity) {
		
		return (Integer) super.save(entity);
		
	}

	@Override
	public void update(Object entity) {
		
		super.update(entity);
	}

	@Override
	public void delete(Object entity) {
		
		super.delete(entity);
	}
	
	@Override
	public void delete(Integer key) {
		
		this.delete(this.get(key));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getResultList(Class<T> entityClass, String where,
			LinkedHashMap<String, String> orderBy, final Object... args) {
	
		final String queryStr = "from " + entityClass.getSimpleName() + 
				" as a " + where + buildOrderby(orderBy);
		
		List<T> list = super.executeFind(new HibernateCallback<Object>() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(queryStr);
			
				if(args != null) {
				
					for (int i = 0 ; i < args.length ; i++) {
					
						query.setParameter( i, args[i]);
					}
				}
				
				List<T> result = query.list();
							
				return result;		
			}
		});
			
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getResultList(final Class<T> entityClass, final String where,
			final int firstResult, final int maxResult,
			final LinkedHashMap<String, String> orderBy, final Object... args) {
		
		List<T> list = super.executeFind(new HibernateCallback<Object>() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery("from " + entityClass.getSimpleName() + 
						" as a " + where + buildOrderby(orderBy));
				
				if(args != null) {
					
					for (int i = 0 ; i < args.length ; i++) {
					
						query.setParameter( i, args[i]);
					}
				}
				
				List<T> result = query.setFirstResult(firstResult).setMaxResults(maxResult).list();
							
				return result;		
			}
		});
			
		return list;
	}
	
	private static String buildOrderby(LinkedHashMap<String , String> orderby) {
		
		StringBuffer out = new StringBuffer("");
	
		if(orderby != null && orderby.size() > 0)
		{
			//添加order by 子句
			out.append(" order by ");
			
			//遍历LinkedHashMap中的每个key-value对，
			//每个key-value对生成一个排序条件
			for(String key : orderby.keySet()) {
		
				out.append("a." + key + " " + orderby.get(key));
				out.append(",");
			}
			
			out.deleteCharAt(out.length()-1);
		}
	
		return out.toString();
	}
}
