package com.project.dvdStore.orders;

import java.util.ArrayList;

import dbHandler.DBHandler;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("orders")
public class OrderseResources {
	ArrayList<Integer> dvds_id = new ArrayList<Integer>();
	
	
	
	DBHandler db = new DBHandler();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<Order> getOrders(){
		ArrayList<Order> orders = new ArrayList<Order>();
		
		orders = db.getOrders();
		
		return orders;
	}
	
	@GET
	@Path("order/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Order getOrder(@PathParam("id") int id) {
		Order order = db.getOrder(id);
		
		return order;
	}

	@POST
	@Path("order/add")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createOrder(Order o) {
		
		db.addOrder(o);
		
		
		return Response.ok("Order added", MediaType.TEXT_HTML).build();
		
	}


	//creates a new order
	@POST
	@Path("order/dvd/createOrder")
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response newOrder(Order order) {
		boolean bo = db.createNewOrder(order);
		
		if(bo) {
			return Response.ok("Order created",MediaType.TEXT_HTML).build();
		}else {
			return Response.notAcceptable(null).build();
		}
		
		
		
	}
	
	@POST
	@Path("order/dvd/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void orderDVDs(@PathParam("id") int id,ArrayList<Integer> dvds) {
		System.out.println(dvds);
		for(Integer dvd : dvds) {
			db.addDVDOrder(id, dvd);
		}
	}
	
	@GET
	@Path("order/search/{search}")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<Order> searchOrder(@PathParam("search") String search){
		return db.searchOrder(search);
	}
	
	
	@GET
	@Path("order/test")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Order test(){
		System.out.println(dvds_id.size()==0);
		dvds_id.add(1);
		dvds_id.add(2);
		dvds_id.add(3);
		dvds_id.add(4);
		dvds_id.add(5);
		dvds_id.add(6);
		Order test = new Order(1, "addrefgtr",1, 3, "sctive", "22-01-2021", "pending", dvds_id);
		return test;
	}
	
	@GET
	@Path("order/changestate/{id}/{state}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response changeState(@PathParam("id") int id, @PathParam("state") String state) {
		boolean answer = db.changeStateOrder(id, state);
		if(answer) {
			return Response.ok("Orders state changed to: "+state).build();
		}else {
			return Response.ok("There was an error").build();
		}
	}
	
	//---STATS---
	@GET
	@Path("stats/completed")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response completed() {
		return Response.ok("Completed orders: "+db.statsOrdersCompleted()).build();
	}
	@GET
	@Path("stats/canceled")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response canceled() {
		return Response.ok("Canceled orders: "+db.statsOrdersCanceled()).build();
	}
	@GET
	@Path("stats/active")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response active() {
		return Response.ok("Stil active: "+db.statsOrdersActive()).build();
	}
}
