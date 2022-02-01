package com.project.dvdStore.orders;

import java.util.HashMap;

import com.project.dvdStore.users.User;

import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ShoppingCard {
	
	private int id;
	private User user;
	private int user_id;
	private int amount;
	private HashMap<DVD,Integer> payments = new HashMap<DVD, Integer>();
	private String creDate;
	private String state;//active,freezed,canceled,finished
	
	public ShoppingCard() {
		
	}
	
	
	
	public ShoppingCard(int id, int user_id, int amount, HashMap<DVD, Integer> payments, String creDate, String state) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.amount = amount;
		this.payments = payments;
		this.creDate = creDate;
		this.state = state;
	}



	//constructor
	public ShoppingCard(int id, User user, int amount, String creDate, String state) {
		super();
		this.id = id;
		this.user = user;
		this.amount = amount;
		this.creDate = creDate;
		this.state = state;
	}
	
	

	public int getUser_id() {
		return user_id;
	}



	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public HashMap<DVD, Integer> getPayments() {
		return payments;
	}

	public void setPayments(HashMap<DVD, Integer> payments) {
		this.payments = payments;
	}

	public String getCreDate() {
		return creDate;
	}

	public void setCreDate(String creDate) {
		this.creDate = creDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
}
