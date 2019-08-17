package com.app.walletbuddy.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.app.walletbuddy.model.Category;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addCategory(Category c) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(c);
	}

	@Override
	public void updateCategory(Category c) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> listCategory(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Category> categoryList = session
				.createQuery("from Category C WHERE C.userId='" + userId + "' ORDER BY C.id DESC").list();
		session.clear();
		return categoryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Category getCategoryById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Category> catList = session.createQuery("from Category C WHERE C.id='" + id + "'").list();
		if (catList.size() > 0)
			return catList.get(0);
		else
			return null;
	}

	@Override
	public void removeCategory(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Category c = new Category();
		c.setId(id);
		session.delete(c);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Category getSocialExpenceCategory(int userId) {
		Session session = this.sessionFactory.getCurrentSession();

		List<Category> catList = session
				.createQuery("from Category C WHERE C.name='" + Category.EXPENCE + "' and C.userId='" + userId + "'")
				.list();
		if (catList.size() > 0)
			return catList.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Category getSocialIncomeCategory(int userId) {
		Session session = this.sessionFactory.getCurrentSession();

		List<Category> catList = session
				.createQuery("from Category C WHERE C.name='" + Category.INCOME + "' and C.userId='" + userId + "'")
				.list();
		if (catList.size() > 0)
			return catList.get(0);
		else
			return null;
	}
}
