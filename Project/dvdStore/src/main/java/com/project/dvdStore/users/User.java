package com.project.dvdStore.users;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private int id;
	private String username;
	private String password;
	private String fullName;
	private int role;//1:user, 2:employee ,3:admin
	private String addr;
	private String card;//debit or credit
	private int cardNum;
	private String cardExpDate;
	private String cardPass;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(int id,String username, String password, String fullName, int role, String addr, String card, int cardNum,
			String cardExpDate, String cardPass) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.role = role;
		this.addr = addr;
		this.card = card;
		this.cardNum = cardNum;
		this.cardExpDate = cardExpDate;
		this.cardPass = cardPass;
	}
	
//	public User(String username, String password, String fullName, int role, String addr, String card, int cardNum,
//			String cardExpDate, String cardPass) {
//		super();
//		this.username = username;
//		this.password = password;
//		this.fullName = fullName;
//		this.role = role;
//		this.addr = addr;
//		this.card = card;
//		this.cardNum = cardNum;
//		this.cardExpDate = cardExpDate;
//		this.cardPass = cardPass;
//	}
	
	

	
	
	public String getUsername() {
		return username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public int getCardNum() {
		return cardNum;
	}

	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardExpDate() {
		return cardExpDate;
	}

	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}

	public String getCardPass() {
		return cardPass;
	}

	public void setCardPass(String cardPass) {
		this.cardPass = cardPass;
	}
	
	
	
	
	
	
}
