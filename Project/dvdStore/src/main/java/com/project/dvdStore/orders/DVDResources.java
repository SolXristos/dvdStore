package com.project.dvdStore.orders;

import java.util.ArrayList;

import dbHandler.DBHandler;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("dvds")
public class DVDResources {
	DBHandler db = new DBHandler();
	
//	@GET
//	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
//	public Response getDVDs() {
//		
//		String answer = "<html><p> Not build yet</html>";	
//		return Response.ok(answer, MediaType.TEXT_HTML).build();
//	}
	
	//sends all the dvds to user
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<DVD> getDVDsXML(){
		ArrayList<DVD> dvds = new ArrayList<DVD>();
		
		dvds = db.getDVDs();
		
		
		return dvds;
	}
	
	//send specific dvd to user with given id
	@GET
	@Path("dvd/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public DVD getDVDXML(@PathParam("id") int id){
		
		DVD dvd = db.getDVD(id);
		System.out.println(dvd);
		return dvd;
	}
	
	//send the orders a dvd is in
	@GET
	@Path("dvd/user/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<Order> getDVDOrder(@PathParam("id") int id){
		ArrayList<Order> orders = db.getDVDOrders(id);
		
		return orders;
	}
	
	//add a new dvd to the dvds table
	@POST
	@Path("dvd/add")
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public DVD addDVDXML(DVD d) {
		db.addDVD(d);
		
		return d;
	}
	
	@DELETE
	@Path("dvd/delete/{id}")
	public Response deleteDVD(@PathParam("id") int id) {
		
		db.deleteDVD(id);
		
		return Response.ok("deleted", MediaType.TEXT_HTML).build();
	}
	
	//---STATS----
	@GET
	@Path("stats/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response dvdStat(@PathParam("id") int id) {
		return Response.ok("There are : " + db.DVDOrderTimes(id) + " dvds ordered").build();
	}
	@GET
	@Path("stats")
	@Produces(MediaType.APPLICATION_XML)
	public Response dvdStats() {
		return db.DVDsOrderTimes();
	}
}
