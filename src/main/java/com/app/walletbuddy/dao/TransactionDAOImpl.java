package com.app.walletbuddy.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.app.walletbuddy.model.Category;
import com.app.walletbuddy.model.Transaction;
import com.app.walletbuddy.utils.TransactionView;

public class TransactionDAOImpl implements TransactionDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public void addTransaction(Transaction t) {
		Session session = this.sessionFactory.getCurrentSession();
		t.setRealPrice(t.getColor().indexOf("red") > -1 ? -1 * t.getPrice() : t.getPrice());
		session.persist(t);
	}

	@Override
	public void updateTransaction(Transaction t) {
		Session session = this.sessionFactory.getCurrentSession();
		t.setRealPrice(t.getColor().indexOf("red") > -1 ? -1 * t.getPrice() : t.getPrice());
		session.update(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> listTransactions() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Transaction> transactionList = session.createQuery("from Transaction T  ORDER BY T.date desc").list();
		return transactionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listTransactionsView(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Object[]> transactionList = session.createSQLQuery(
				"select t.id, t.date, t.note, t.color, t.price, c.image, c.user_id, c.id AS catid, c.name from transactions t, categories c WHERE t.category_id=c.id AND c.user_id='"
						+ userId + "'  ORDER BY t.date desc")
				.list();
		return transactionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listTransactionsViewLike(String pattern, int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Object[]> transactionList = session.createSQLQuery(
				"select t.id, t.date, t.note, t.color, t.price, c.image, c.user_id, c.id AS catid, c.name from transactions t, categories c WHERE t.category_id=c.id AND c.user_id='"
						+ userId + "' and t.note RLIKE '" + pattern + "'  ORDER BY t.date desc")
				.list();
		return transactionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Transaction getTransactionById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Transaction> transactionsList = session.createQuery("from Transaction T WHERE T.id='" + id + "'").list();
		if (transactionsList.size() > 0) {
			return transactionsList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void removeTransaction(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction t = new Transaction();
		t.setId(id);
		session.delete(t);
	}

	@Override
	public void removeTransaction(Transaction t) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listTransactionsViewPriceBelow(float price, int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Object[]> transactionList = session.createSQLQuery(
				"select t.id, t.date, t.note, t.color, t.price, c.image, c.user_id, c.id AS catid, c.name from transactions t, categories c WHERE t.category_id=c.id AND c.user_id='"
						+ userId + "' and t.price < '" + price + "'  ORDER BY t.price desc")
				.list();
		return transactionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listTransactionsViewCategories(String pattern, int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Object[]> transactionList = session.createSQLQuery(
				"select t.id, t.date, t.note, t.color, t.price, c.image, c.user_id, c.id AS catid, c.name from transactions t, categories c WHERE t.category_id=c.id AND c.user_id='"
						+ userId + "' and c.name rlike '" + pattern + "'  ORDER BY c.name desc").list();
		return transactionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listTransactionsMyWalletBallance(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Object[]> transactionList = session.createSQLQuery(
				"select sum(t.real_price), t.date from transactions t, categories c where t.category_id=c.id and c.user_id='"
						+ userId + "' group by t.date order by t.date").list();
		return transactionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listTransactionsMyWalletPositiveBallance(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Object[]> transactionList = session.createSQLQuery(
				"select sum(t.real_price), t.date from transactions t, categories c where t.category_id=c.id and c.user_id='"
						+ userId + "' and t.color='green' group by t.date order by t.date").list();
		return transactionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listTransactionsSumByCategories(String catColor, int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Object[]> transactionList = session.createSQLQuery(
				"select sum(t.real_price), c.name from transactions t, categories c where t.category_id=c.id and c.user_id='"
						+ userId + "' and t.color='" + catColor + "' group by c.name").list();
		return transactionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> listTransactionsByCategory(int catId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Transaction> transactionsList = session
				.createQuery("from Transaction T WHERE T.categoryId='" + catId + "'").list();
		return transactionsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransactionView getSocIncomeAgregation(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		TransactionView tv = null;
		List<Object[]> viewAggr = session.createSQLQuery(
				"select sum(t.real_price) as summ from transactions as t where t.category_id in( select c.id from categories as c where c.user_id='"
						+ userId + "' and c.name='" + Category.INCOME + "' )")
				.list();
		if (viewAggr != null && viewAggr.size() > 0 && viewAggr.get(0) != null) {
			tv = new TransactionView();
			tv.setCategoryName(Category.INCOME);
			tv.setColor("green");
			tv.setNote("Aggregation");
			tv.setImage("6fe35b072a948605673b07eea86f4a58.jpg");
			tv.setPrice(String.format("%.02f", Float.parseFloat("" + viewAggr.get(0))));
			tv.setCategoryId(-1);
			tv.setTransactionId(-1);
		}
		return tv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransactionView getSocExpenceAgregation(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		TransactionView tv = null;
		List<Object[]> viewAggr = session.createSQLQuery(
				" select sum(t.real_price) as summ from transactions as t where t.category_id in( select c.id from categories as c where c.user_id='"
						+ userId + "' and c.name='" + Category.EXPENCE + "' )").list();
		if (viewAggr != null && viewAggr.size() > 0 && viewAggr.get(0) != null) {
			tv = new TransactionView();
			tv.setCategoryName(Category.EXPENCE);
			tv.setColor("red");
			tv.setNote("Aggregation");
			tv.setImage("sex.jpg");
			tv.setPrice(String.format("%.02f", Float.parseFloat("" + viewAggr.get(0))));
			tv.setCategoryId(-1);
			tv.setTransactionId(-1);
		}
		return tv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getIncomeTransactionsValueAggregation(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "select sum(t.real_price) from categories as c, transactions as t where t.category_id=c.id and c.user_id='"
				+ userId + "' and t.color='green'";
		try {
			List<Double> sum = session.createSQLQuery(sql).list();
			return sum.size() > 0 ? (int) Math.round(sum.get(0)) : 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getExpenceTransactionsValueAggregation(int userId) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "select sum(t.price) from categories as c, transactions as t where t.category_id=c.id and c.user_id='"
				+ userId + "' and t.color='red'";
		try {
			List<Double> sum = session.createSQLQuery(sql).list();
			return sum.size() > 0 ? (int) Math.round(sum.get(0)) : 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

}
