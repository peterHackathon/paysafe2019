package com.app.walletbuddy.service;

import java.util.List;

import com.app.walletbuddy.model.Category;

public interface CategoryService {

	void addCategory(Category c);

	void updateCategory(Category c);

	List<Category> listCategory(int userId);

	Category getCategoryById(int id);

	Category getSocialIncomeCategory(int userId);

	Category getSocialExpenceCategory(int userId);

	void removeCategory(int id);

}
