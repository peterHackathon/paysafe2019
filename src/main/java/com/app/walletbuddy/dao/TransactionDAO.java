package com.app.walletbuddy.dao;

import java.util.List;

import com.app.walletbuddy.model.Transaction;
import com.app.walletbuddy.utils.TransactionView;

public interface TransactionDAO {

	void addTransaction(Transaction t);

	void updateTransaction(Transaction t);

	List<Transaction> listTransactions();

	List<Transaction> listTransactionsByCategory(int catId);

	Transaction getTransactionById(int id);

	void removeTransaction(int id);

	void removeTransaction(Transaction t);

	List<Object[]> listTransactionsView(int userId);

	List<Object[]> listTransactionsViewLike(String pattern, int userId);

	List<Object[]> listTransactionsViewPriceBelow(float price, int userId);

	List<Object[]> listTransactionsViewCategories(String pattern, int userId);

	List<Object[]> listTransactionsMyWalletBallance(int userId);

	List<Object[]> listTransactionsMyWalletPositiveBallance(int userId);

	List<Object[]> listTransactionsSumByCategories(String catColor, int userId);

	TransactionView getSocIncomeAgregation(int userId);

	TransactionView getSocExpenceAgregation(int userId);
	
	int getExpenceTransactionsValueAggregation(int userId);
	
	int getIncomeTransactionsValueAggregation(int userId);
}
