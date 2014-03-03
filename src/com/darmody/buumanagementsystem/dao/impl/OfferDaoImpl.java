package com.darmody.buumanagementsystem.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.darmody.buumanagementsystem.dao.OfferDao;
import com.darmody.buumanagementsystem.entity.Student;
import com.darmody.buumanagementsystem.entity.offer.Offer;

/**
 * 2013.9.5
 * @author Darmody
 * @content 报盘数据操作实现类
 */
@Repository
public class OfferDaoImpl extends DaoImpl implements OfferDao{

	@Override
	public Offer get(Integer pk) {

		return this.get(Offer.class, (Serializable)pk);
	}

	@Override
	public List<Offer> getOffers() {

		return this.getResultList(Offer.class, "", null, (Object[])null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Offer getOfferByOfferUrl(String url) {
		
		String hql = "from Offer p where p.offerUrl like '%" + url + "%'";
		
		List<Offer> offers = this.find(hql);
		
		if(offers != null && offers.size() == 1) {
		
			return offers.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Offer> getOffers(final int firstResult, final int maxResult) {
		
		final String hql = "from Offer p order by p.offerCreateDate desc";
		
		List<Offer> list = super.executeFind(new HibernateCallback<Object>() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(hql);
				
				List<Student> result = query.setFirstResult(firstResult).setMaxResults(maxResult).list();
							
				return result;		
			}
		});
		
		return list;
	}
}
