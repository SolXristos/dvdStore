package dbHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.dvdStore.orders.DVD;
import com.project.dvdStore.orders.Order;
import com.project.dvdStore.orders.ShoppingCard;
import com.project.dvdStore.users.User;

import jakarta.ws.rs.core.Response;

//this class handle's the database. It connects with the database we created in MySQL workbench
//We take values from the db, we change values and we delete values, using these class
public class DBHandler {
	File file = new File("D:\\solxr\\Μ.Π.Ε.Σ\\Ε' Έτος\\Θ Εξάμηνο\\Τεχνολογία Λογισμικού\\Project\\dvdStore\\activeOrders.pdf");
	Connection con = null;
	
	public DBHandler() {
		String url = "jdbc:mysql://localhost:3306/dvdstore?autoReconnect=true&useSSL=false";
		String username = "root";
		String password = "1234";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);

		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

	// ---------------------------USERS-------------------------------
	// return from db all the users
	public ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();

		// sql query
		String sql = "select * from users";
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			// iterate through the returned values and create the user and add them to the
			// list
			while (rs.next()) {
				User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10));
				users.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}

	// returns a specific user, based on the given id
	public User getUser(int id) {
		User user = null;

		String sql = "select * drom users where idUsers = " + id;

		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			// if the db sends us a value we create the new user, otherwise we send null
			if (rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	//checks if the username exists
	public boolean checkUser(String username) {
		

		String sql = "select * from users where username =  ?" ;

		PreparedStatement st;
		try {
			st = con.prepareStatement(sql);
			st.setString(1, username);
			//st.executeQuery();
			ResultSet rs = st.executeQuery();

			// if the db sends us a value we create the new user, otherwise we send null
			if (rs.next()) {
				
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	// verify user
	public User logUser(String username, String password) {
		User user = null;
		String sql = "select * from users where username =? and password = ?" ;

		PreparedStatement st;
		try {
			st = con.prepareStatement(sql);
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();

			// if the db sends us a value we create the new user, otherwise we send null
			if (rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	// add user
	public boolean addUser(User u) {
		// Regex to check valid username.
        String regex = "^[A-Za-z]\\w{5,29}$";
  
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
  
        // If the username is empty
        // return false
        if (u.getUsername() == null) {
            return false;
        }
  
        // Pattern class contains matcher() method
        // to find matching between given username
        // and regular expression.
        Matcher m = p.matcher(u.getUsername());
        if(!m.matches()) {
        	return false;
        }
        
        //validates password
     // Regex to check valid password.
        regex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
  
        // Compile the ReGex
        p = Pattern.compile(regex);
  
        // If the password is empty
        // return false
        if (u.getPassword() == null) {
            return false;
        }
  
        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m1 = p.matcher(u.getPassword());
        
        if(!m1.matches()) {
        	return false;
        }
        
        //check if username exists
		boolean check = checkUser(u.getUsername());

		if (!check) {
			String sql = "insert into users values (?,?,?,?,?,?,?,?,?,?)";

			try {
				// prepare statement for sql query
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, u.getId());
				st.setString(2, u.getUsername());
				st.setString(3, u.getPassword());
				st.setString(4, u.getFullName());
				st.setInt(5, u.getRole());
				st.setString(6, u.getAddr());
				st.setString(7, u.getCard());
				st.setInt(8, u.getCardNum());
				st.setString(9, u.getCardExpDate());
				st.setString(10, u.getCardPass());

				st.executeUpdate();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;

	}

	//change password
	public boolean changePassword(int id, String password,String newPassword) {
		String sql = "update users set password = ? where idUsers = ?";
		boolean check = checkPassword(id,password);
		
		if(check) {
			try {
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1,newPassword);
				st.setInt(2, id);
				st.executeUpdate();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}
	//check if the password is valid 
	public boolean checkPassword(int id, String password) {
		String sql = "select * from users where idUsers = ? and password = ?";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			if(rs.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	// view user's orders
	public ArrayList<Order> getUsersOrders(int id) {
		ArrayList<Order> orders = new ArrayList<Order>();

		// sql query
		String sql = "select * from orders where user_id = " + id;
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			// iterate through the returned values and create the user and add them to the
			// list
			while (rs.next()) {
				Order order = new Order(rs.getInt(1), rs.getString(2), new ShoppingCard(), getUser(rs.getInt(4)),
						rs.getString(5), rs.getString(6), rs.getString(7));
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orders;
	}
	//delete user
	public boolean deleteUser(int id) {
		String sql = "delete from users where idUsers = ?";
		
		PreparedStatement st;
		
		try {
			st  = con.prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// --------------------------ORDERS----------------------------------
	// view all orders
	public ArrayList<Order> getOrders() {
		ArrayList<Order> orders = new ArrayList<Order>();

		// sql query
		String sql = "select * from orders";
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			// iterate through the returned values and create the user and add them to the
			// list
			while (rs.next()) {
				Order order = new Order(rs.getInt(1), rs.getString(2), getShoppingCard(rs.getInt(3)),
						getUser(rs.getInt(4)), rs.getString(5), rs.getString(6), rs.getString(7));
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orders;
	}

	// view specific order with id
	public Order getOrder(int id) {
		Order order = null;

		String sql = "select * from orders where id = " + id;
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				order = new Order(rs.getInt(1), rs.getString(2), getShoppingCard(rs.getInt(3)), getUser(rs.getInt(4)),
						rs.getString(5), rs.getString(6), rs.getString(7));
				return order;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return order;
	}

	// add new order
	public int addOrder(Order o) {
		String sql = "insert into orders values(?,?,?,?,?,curdate(),curdate())";
		Statement s;
		
		int id = 0;
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, o.getId());
			st.setString(2, o.getAddr());
			st.setInt(3, o.getCard_id());
			st.setInt(4, o.getUser_id());
			st.setString(5, o.getState());
//				st.setString(6, o.getDateCreated());
//				st.setString(7, o.getDateFin());
			st.executeUpdate();

			sql = "select id from orders where id = last_insert_id();";
			s = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;
	}

	// delete order
	public void deleteOrder(int id) {
		String sql = "delete from orders where id = ?";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//search order with different values
	public ArrayList<Order> searchOrder(String search) {
		ArrayList<Order> orders  = new ArrayList<Order>();
		String sql = null;
		try {
			int id = Integer.parseInt(search);
			sql = "select * from orders where id = ? or addr = ? or shopingCard_id = ? or user_id = ? or state = ? or date = ? or dateFin = ?";
			try {
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, id);
				st.setString(2, search);
				st.setInt(3, id);
				st.setInt(4, id);
				st.setString(5, search);
				st.setString(6, search);
				st.setString(7, search);
				ResultSet rs= st.executeQuery();
				
				while(rs.next()) {
					Order order = new Order(rs.getInt(1), rs.getString(2), getShoppingCard(rs.getInt(3)), getUser(rs.getInt(4)),
							rs.getString(5), rs.getString(6), rs.getString(7));
					orders.add(order);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NumberFormatException ex){
			
			sql = "select * from orders where  addr = ? or state = ? or date = ? or dateFin = ?";
			try {
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, search);
				st.setString(2, search);
				st.setString(3, search);
				st.setString(4, search);
				//st.execute();
				ResultSet rs= st.executeQuery();
				
				while(rs.next()) {
					Order order = new Order(rs.getInt(1), rs.getString(2), getShoppingCard(rs.getInt(3)), getUser(rs.getInt(4)),
							rs.getString(5), rs.getString(6), rs.getString(7));
					orders.add(order);
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
		
		return orders;
	}
	
	//create new Order
	public boolean createNewOrder(Order o) {
		
		//order has no DVDs
		if(o.getDvds_id().size()==0) {
			return false;
		} else {
			String sql = "insert into orders values(?,?,?,?,?,curdate(),curdate())";
			Statement s;
			
			int id = 0;
			try {
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, o.getId());
				st.setString(2, o.getAddr());
				st.setInt(3, o.getCard_id());
				st.setInt(4, o.getUser_id());
				st.setString(5, o.getState());
//					st.setString(6, o.getDateCreated());
//					st.setString(7, o.getDateFin());
				st.executeUpdate();

				sql = "select id from orders where id = last_insert_id();";
				s = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				rs.next();
				id = rs.getInt(1);
				//adds order and DVDs to dvdOrder table to store the order's dvds
				for(Integer dvd : o.getDvds_id()) {
					addDVDOrder(dvd, id);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return true;
	}
	
	//cancel an order
	public boolean changeStateOrder(int id, String state) {
		String sql = "update orders set state = ? where id = ? ";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, state);
			st.setInt(2, id);
			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	// ----------------------DVD---------------------------------------------------
	// view all DVDs
	public ArrayList<DVD> getDVDs() {
		ArrayList<DVD> dvds = new ArrayList<DVD>();

		String sql = "select * from dvds";

		Statement st;

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				DVD d = new DVD(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getFloat(10),
						rs.getInt(11));
				dvds.add(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dvds;
	}

	public DVD getDVD(int id) {
		DVD d = null;

		String sql = "select * from dvds where idDVDs = " + id;

		Statement st;

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				d = new DVD(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getFloat(10),
						rs.getInt(11));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return d;
	}
	//get dvds title
	public String getDVDTitle(int id) {
		String sql = "select title from dvds where idDVDs = " + id;

		Statement st;

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			System.out.println("title is: "+rs.getString(1));
			return rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// add new DVD
	public void addDVD(DVD d) {
		String sql = "insert into dvds values (?,?,?,?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, d.getId());
			st.setString(2, d.getTitle());
			st.setString(3, d.getActors());
			st.setString(4, d.getDirector());
			st.setString(5, d.getDate());
			st.setInt(6, d.getDuration());
			st.setString(7, d.getLang());
			st.setString(8, d.getSubs());
			st.setString(9, d.getKind());
			st.setFloat(10, d.getPrice());
			st.setInt(11, d.getPieces());
			st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// delete dvd
	public void deleteDVD(int id) {
		String sql = "delete from dvds where idDVDs = ?";

		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ----------------------SHOPPINGCARDS--------------------------------------------
	// view all cards
	public ArrayList<ShoppingCard> getShoppingCards() {
		ArrayList<ShoppingCard> cards = new ArrayList<ShoppingCard>();
		String sql = "select * from shopingcard";

		Statement st;

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				ShoppingCard c = new ShoppingCard(rs.getInt(1), getUser(rs.getInt(5)), rs.getInt(2), rs.getString(3),
						rs.getString(4));
				cards.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cards;
	}

	// view specific card
	public ShoppingCard getShoppingCard(int id) {
		ShoppingCard card = null;
		String sql = "select * from shopingcard where idshopingCard = " + id;
		Statement st;

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				card = new ShoppingCard(rs.getInt(1), getUser(rs.getInt(5)), rs.getInt(2), rs.getString(3),
						rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return card;
	}

	//view shopping card based on user_id
	public ArrayList<ShoppingCard> UsersShoppingCard(int id) {
			ArrayList<ShoppingCard> cards = new ArrayList<ShoppingCard>();
			String sql = "select * from shopingcard where userid = " + id;
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next()) {
					ShoppingCard c = new ShoppingCard(rs.getInt(1), getUser(rs.getInt(5)), rs.getInt(2), rs.getString(3),rs.getString(4));
					cards.add(c);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cards;
	}
	// add new card
	public void addShoppingCard(ShoppingCard c) {

		String sql = "insert into shopingcard values(?,?,curdate(),?,?)";

		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, c.getId());
			st.setInt(2, c.getAmount());
			st.setString(4, c.getState());
			st.setInt(5, c.getUser_id());
			st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//deletes a shopping card
	public void deleteShoppingCard(int id) {
		String sql = "delete from shopingcard where idshopingcard = ?";
		
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//change the state of a card --> //active,freezed,canceled,finished
	public void changeStateShoppingCard(int id, String state) {
		String sql = "update shopingcard set state = ? where idshopingcard = ?";
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, state);
			st.setInt(2, id);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// ----------------------CONNECTION: DVDS WITH ORDERS (MANY TO MANY)----------------

	public void addDVDOrder(int dvd_id, int order_id) {
		String sql = "insert into dvdorders values(?,?,?)";
		System.out.println("\n\n" + dvd_id + " " + order_id + "\n\n");
		try {

			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, 0);
			st.setInt(3, order_id);
			st.setInt(2, dvd_id);
			st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// view the DVDs an order has -> the id is orders id
	public ArrayList<DVD> getOrderDVD(int id) {
		ArrayList<DVD> dvds = new ArrayList<DVD>();

		String sql = "select dvds.* from dvds join dvdorders on dvds.idDVDs = dvdorders.id_dvd where dvdorders.id_order = "
				+ id;

		Statement st;

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				DVD d = new DVD(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getFloat(10),
						rs.getInt(11));
				dvds.add(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dvds;
	}

	// view all orders from dvd ->id from dvd
	public ArrayList<Order> getDVDOrders(int id) {
		ArrayList<Order> orders = new ArrayList<Order>();
		String sql = "select orders.* from orders join dvdorders on orders.id = dvdorders.id_order where dvdorders.id_dvd = "
				+ id;

		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Order order = new Order(rs.getInt(1), rs.getString(2), getShoppingCard(rs.getInt(3)),
						getUser(rs.getInt(4)), rs.getString(5), rs.getString(6), rs.getString(7));
				orders.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return orders;
	}

	// -------------CONNECTION: SHOPPINGCARDS WITH
	// DVDS---------------------------------
	// add values
	public void addDVDCard(int dvd_id, int card_id) {
		String sql = "insert into dvdcards values(?,?,?)";

		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, 0);
			st.setInt(2, dvd_id);
			st.setInt(3, card_id);
			st.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// take the DVDs of a given card->id from card
	public ArrayList<DVD> getCardDVD(int id) {
		ArrayList<DVD> dvds = new ArrayList<DVD>();
		String sql = "select dvds.* from dvds join dvdcards on dvds.idDVDs = dvdcards.dvd_id where dvdcards.card_id = "
				+ id;

		Statement st;

		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				DVD d = new DVD(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getFloat(10),
						rs.getInt(11));
				dvds.add(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dvds;
	}

	//----------------------STATISTICS----------------------------------------------------
	//completed orders
	public int statsOrdersCompleted() {
		String sql = "select * from orders where state = 'completed'";
		
		Statement st;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			int count = 0;
			while(rs.next()) {
				count++;
			}
			 Document document = new Document();
		      try
		      {
		    	  
		         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		         document.open();
		         document.add(new Paragraph("There are " + count + " completed orders"));
		         document.close();
		         writer.close();
		      } catch (DocumentException e)
		      {
		         e.printStackTrace();
		      } catch (FileNotFoundException e)
		      {
		         e.printStackTrace();
		      }
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	//canceled orders
	public int statsOrdersCanceled() {
		String sql = "select * from orders where state = 'canceled'";
		
		Statement st;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			int count = 0;
			while(rs.next()) {
				count++;
			}
			 Document document = new Document();
		      try
		      {
		    	  
		         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\solxr\\Μ.Π.Ε.Σ\\Ε' Έτος\\Θ Εξάμηνο\\Τεχνολογία Λογισμικού\\Project\\dvdStore\\canceledOrders.pdf"));
		         document.open();
		         document.add(new Paragraph("There are " + count + " canceled orders"));
		         document.close();
		         writer.close();
		      } catch (DocumentException e)
		      {
		         e.printStackTrace();
		      } catch (FileNotFoundException e)
		      {
		         e.printStackTrace();
		      }
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	//active orders
	public int statsOrdersActive() {
		String sql = "select * from orders where state = 'active'";
		
		Statement st;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			int count = 0;
			while(rs.next()) {
				count++;
			}
			//creates pdf file
			 Document document = new Document();
		      try
		      {
		    	  
		         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		         document.open();
		         document.add(new Paragraph("There are " + count + " active orders"));
		         document.close();
		         writer.close();
		      } catch (DocumentException e)
		      {
		         e.printStackTrace();
		      } catch (FileNotFoundException e)
		      {
		         e.printStackTrace();
		      }
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	//completed orders
	public int statsOrdersCompletedUser(int id) {
		String sql = "select * from orders where state = 'completed' and user_id = " + id;
		
		Statement st;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			int count = 0;
			while(rs.next()) {
				count++;
			}
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	//canceled orders
	public int statsOrdersCanceledUser(int id) {
		String sql = "select * from orders where state = 'canceled' and user_id = " +id;
		
		Statement st;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			int count = 0;
			while(rs.next()) {
				count++;
			}
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	//active orders
	public int statsOrdersActiveUser(int id) {
		String sql = "select * from orders where state = 'active' and user_id = " + id;
		
		Statement st;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			int count = 0;
			while(rs.next()) {
				count++;
			}
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	//ordered dvd
	public int DVDOrderTimes(int id) {
		String sql = "select orders.* from orders join dvdorders on orders.id = dvdorders.id_order where dvdorders.id_dvd = " + id;
		Statement st;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int count = 0;
			while(rs.next()) {
				count++;
			}
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	//orders for every dvd
	public Response DVDsOrderTimes() {
		String sql = "select * from dvdorders " ;
		Statement st;
		//File file = new File("D:\\solxr\\Μ.Π.Ε.Σ\\Ε' Έτος\\Θ Εξάμηνο\\Τεχνολογία Λογισμικού\\Project\\dvdStore\\canceledOrders.pdf");
		if(file.exists()) {
			file.delete();
		}
		try {
			String response = "";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int count = 0;
			while(rs.next()) {
				count = DVDOrderTimes(rs.getInt(1));
				System.out.println("\n\nid: "+count);
				String title = getDVDTitle(rs.getInt(2));
				response += title+" dvd has sold: " +  count +"(id="+rs.getInt(1)+")\n";
				
			}
			 Document document = new Document();
		      try
		      {
		    	  
		         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file,true));
		         document.open();
		         document.add(new Paragraph(response));
		         document.close();
		         writer.close();
		      } catch (DocumentException e)
		      {
		         e.printStackTrace();
		      } catch (FileNotFoundException e)
		      {
		         e.printStackTrace();
		      }
			return Response.ok(""+response).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
