package com.app.walletbuddy.dao;

import java.util.List;

import com.app.walletbuddy.model.Category;

public interface CategoryDAO {

	void addCategory(Category c);

	void updateCategory(Category c);

	List<Category> listCategory(int userId);

	Category getCategoryById(int id);

	void removeCategory(int id);

	Category getSocialExpenceCategory(int userId);

	Category getSocialIncomeCategory(int userId);

}
