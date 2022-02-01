package com.project.dvdStore.orders;

import java.util.ArrayList;

import com.project.dvdStore.users.User;

import jakarta.xml.bind.annotation.XmlRootElement;

//class to handle orders
@XmlRootElement
public class Order {
	
	private int id;
	private String addr;
	private ShoppingCard card;
	private int card_id;
	private User user;
	private int user_id;
	private String state;//creates,charged,canceled,in process, finished
	private String dateCreated;
	private String dateFin;
	private ArrayList<Integer> dvds_id;
	
	public Order() {
		
	}
	
	
	
	public Order(int id, String addr, int card_id, int user_id, String state, String dateCreated, String dateFin,
			ArrayList<Integer> dvds_id) {
		super();
		this.id = id;
		this.addr = addr;
		this.card_id = card_id;
		this.user_id = user_id;
		this.state = state;
		this.dateCreated = dateCreated;
		this.dateFin = dateFin;
		this.setDvds_id(dvds_id);
	}



	public Order(int id, String addr, int card_id, int user_id, String state, String dateCreated, String dateFin) {
		super();
		this.id = id;
		this.addr = addr;
		this.card_id = card_id;
		this.user_id = user_id;
		this.state = state;
		this.dateCreated = dateCreated;
		this.dateFin = dateFin;
	}



	//constructor
	public Order(int id, String addr, ShoppingCard card, User user, String state, String dateCreated, String dateFin) {
		super();
		this.id = id;
		this.addr = addr;
		this.card = card;
		this.user = user;
		this.state = state;
		this.dateCreated = dateCreated;
		this.dateFin = dateFin;
	}
	
	//setters getters
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", addr=" + addr + ", card_id=" + card_id + ", user_id=" + user_id + ", state="
				+ state + ", dateCreated=" + dateCreated + ", dateFin=" + dateFin + "]";
	}
	
	
	public int getCard_id() {
		return card_id;
	}


	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public ShoppingCard getCard() {
		return card;
	}

	public void setCard(ShoppingCard card) {
		this.card = card;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddr() {
		return addr;
	}
	
	public int getId() {
		return id;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDateFin() {
		return dateFin;
	}
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}



	public ArrayList<Integer> getDvds_id() {
		return dvds_id;
	}



	public void setDvds_id(ArrayList<Integer> dvds_id) {
		this.dvds_id = dvds_id;
	}
	
	
	
}
