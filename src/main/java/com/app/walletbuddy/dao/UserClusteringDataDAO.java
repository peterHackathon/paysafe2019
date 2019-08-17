package com.app.walletbuddy.dao;

import com.app.walletbuddy.model.UserClusteringData;

public interface UserClusteringDataDAO {
	void addUserClusteringData(UserClusteringData data);

	void updateUserClusteringData(UserClusteringData data);

	UserClusteringData getUserClusteringData(int id);
}
