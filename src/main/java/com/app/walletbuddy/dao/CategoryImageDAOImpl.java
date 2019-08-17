package com.app.walletbuddy.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.app.walletbuddy.model.CategoryImage;

@Repository
public class CategoryImageDAOImpl implements CategoryImageDAO{

    private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getCategoryImage(int id) {
		Session session = this.sessionFactory.getCurrentSession(); 
		List<String> imgList = session.createQuery("from CategoryImage CI WHERE CI.id='"+id+"'").list();
		if(imgList.size()>0)
			   return imgList.get(0);
			else return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryImage> getCategoryImages() {
		Session session = this.sessionFactory.getCurrentSession(); 
		return  session.createQuery("from CategoryImage").list();
	}
}
