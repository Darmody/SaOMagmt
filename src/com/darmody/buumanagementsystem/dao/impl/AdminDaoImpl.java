package com.darmody.buumanagementsystem.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.darmody.buumanagementsystem.dao.AdminDao;
import com.darmody.buumanagementsystem.entity.Administrator;
import com.darmody.buumanagementsystem.entity.Student;

/**
 * 2013.7.25 16:07
 * @author Caihuanyu
 * @content 管理员数据操作实现类
 */

@Repository
public class AdminDaoImpl extends DaoImpl implements AdminDao{

	@Override
	public Administrator get(Integer pk) {
		
		return this.get(Administrator.class, pk);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Administrator getAdminAccountAndPass(String loginAccount,
			String password) {

		List<Administrator> administrators = this.find(
				"from Administrator p where p.loginAccount = ? and p.password=?", 
				loginAccount, password);
		
		if(administrators != null && administrators.size() == 1) {
		
			return administrators.get(0);
		}
		
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Administrator getAdminByName(String name) {

		String hql = "from Administrator p where p.name like '%" + name + "%'";
		
		List<Administrator> administrators = this.find(hql);
		
		if(administrators != null && administrators.size() == 1) {
		
			return administrators.get(0);
		}
		
		return null;
	}

	@Override
	public List<Administrator> getAdministrators() {
		
		return this.getResultList(Administrator.class, "", null, (Object[])null);
	}

	@Override
	public void deleteAdministrators(List<Administrator> administrators) {
		
		for(int i = 0; i < administrators.size(); i++) {
			
			this.delete(administrators.get(i).getPK_adminKey());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Administrator getAdminByLoginAccount(String account) {
		
		String hql = "from Administrator p where p.loginAccount = ?";
		
		List<Administrator> administrators = this.find(hql, account);
		
		if(administrators != null && administrators.size() == 1) {
		
			return administrators.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Administrator> getAdministrators(final int firstResult, final int maxResult) {
	
		final String hql = "from Administrator";
		
		List<Administrator> list = super.executeFind(new HibernateCallback<Object>() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(hql);
				
				List<Student> result = query.setFirstResult(firstResult).setMaxResults(maxResult).list();
							
				return result;		
			}
		});
			
		return list;
	}
}