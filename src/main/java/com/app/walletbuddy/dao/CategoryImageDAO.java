package com.app.walletbuddy.dao;

import java.util.List;

import com.app.walletbuddy.model.CategoryImage;

public interface CategoryImageDAO {
	String getCategoryImage(int id);

	List<CategoryImage> getCategoryImages();
}
