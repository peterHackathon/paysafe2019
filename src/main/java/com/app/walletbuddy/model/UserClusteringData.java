package com.app.walletbuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_clustering_data")
public class UserClusteringData {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "age")
	private int age;

	@Column(name = "monthly_income")
	private int monthlyIncome;

	@Column(name = "spending_score")
	private int spendingScore;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(int monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public int getSpendingScore() {
		return spendingScore;
	}

	public void setSpendingScore(int spendingScore) {
		this.spendingScore = spendingScore;
	}

	@Override
	public String toString() {
		return "UserClusteringData [userId=" + userId + ", age=" + age + ", monthlyIncome=" + monthlyIncome
				+ ", spendingScore=" + spendingScore + "]";
	}
}
