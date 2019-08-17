package com.app.walletbuddy.service;

import java.util.List;

import com.app.walletbuddy.model.CategoryImage;

public interface CategoryImageService {
	String getCategoryImage(int id);

	List<CategoryImage> getCategoryImages();
}
