package com.app.walletbuddy.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.app.walletbuddy.model.UserClusteringData;

public class UserClusteringDataDAOImpl implements UserClusteringDataDAO {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addUserClusteringData(UserClusteringData data) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(data);
	}

	@Override
	public void updateUserClusteringData(UserClusteringData data) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserClusteringData getUserClusteringData(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserClusteringData> dataList = session.createQuery("from UserClusteringData D WHERE D.userId='"+userId+"'").list();
		if(dataList.size()>0) {
			return dataList.get(0);
		}
	    return null;
	}
}
