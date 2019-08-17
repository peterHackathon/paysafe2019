package com.app.walletbuddy.dao;

import java.util.List;

import com.app.walletbuddy.model.User;

public interface UserDAO {

	void addUser(User u);

	void updateUser(User u);

	List<User> listUsers();

	User getUserById(int id);

	User getUserByCredentials(String email, String password);

	void removeUser(int id);
}
