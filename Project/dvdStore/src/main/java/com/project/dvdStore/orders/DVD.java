package com.project.dvdStore.orders;



import jakarta.xml.bind.annotation.XmlRootElement;

//class to create and handle DVD's
@XmlRootElement
public class DVD {
	private int id;
	private String title;
	//private ArrayList<String> actors;
	private String actors;
	private String director;
	private String date;
	private int duration;
	//private ArrayList<String> lang;
	private String lang;
	private String subs;
	//private ArrayList<String> subsLang;
	private String kind;
	private float price;
	private int pieces;
	
	public DVD() {
		// TODO Auto-generated constructor stub
	}
	
	//constructor
	public DVD(int id, String title, String actors, String director, String date, int duration,
			String lang, String subs, String kind, float price, int pieces) {
		super();
		this.id = id;
		this.title = title;
		this.actors = actors;
		this.director = director;
		this.date = date;
		this.duration = duration;
		this.lang = lang;
		this.subs = subs;
		this.kind = kind;
		this.price = price;
		this.pieces = pieces;
	}
	
	//setters and getters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getSubs() {
		return subs;
	}

	public void setSubs(String subs) {
		this.subs = subs;
	}

	@Override
	public String toString() {
		return "DVD [id=" + id + ", title=" + title + ", actors=" + actors + ", director=" + director + ", date=" + date
				+ ", duration=" + duration + ", lang=" + lang + ", subs=" + subs + ", kind=" + kind + ", price=" + price
				+ ", pieces=" + pieces + "]";
	}
	
	
	
	
	
	
}
