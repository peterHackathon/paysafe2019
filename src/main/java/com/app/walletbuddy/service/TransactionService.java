package com.app.walletbuddy.service;

import java.util.List;

import com.app.walletbuddy.model.Transaction;
import com.app.walletbuddy.utils.TransactionView;

public interface TransactionService {

	void addTransaction(Transaction t, int userId);

	void addPayment(Transaction fromTransaction, Transaction toTransaction, int fromUserId, int toUserId);

	void updateTransaction(Transaction t, int userId);

	List<Transaction> listTransactions();

	List<Transaction> listTransactionsByCategory(int catId);

	Transaction getTransactionById(int id);

	void removeTransaction(int transId, int userId);

	List<Object[]> listTransactionsView(int userId);

	List<Object[]> listTransactionsViewLike(String pattern, int userId);

	List<Object[]> listTransactionsViewPriceBelow(float price, int userId);

	List<Object[]> listTransactionsViewCategories(String pattern, int userId);

	List<Object[]> listTransactionsMyWalletBallance(int userId);

	List<Object[]> listTransactionsMyWalletPositiveBallance(int userId);

	List<Object[]> listTransactionsSumByExpenceCategories(int userId);

	List<Object[]> listTransactionsSumByIncomeCategories(int userId);

	TransactionView getSocIncomeAgregation(int userId);

	TransactionView getSocExpenceAgregation(int userId);
}
