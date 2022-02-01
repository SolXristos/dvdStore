package com.project.dvdStore.orders;

import java.util.ArrayList;

import dbHandler.DBHandler;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("cards")
public class ShoppingCardResources {
	
	DBHandler db = new DBHandler();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<ShoppingCard> getCards(){
		ArrayList<ShoppingCard> cards = db.getShoppingCards();
		
		return cards;
	}
	
	@GET
	@Path("card/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public ShoppingCard getCard(@PathParam("id") int id) {
		return db.getShoppingCard(id);
	}
	
	@DELETE
	@Path("card/delete/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response deleteCard(@PathParam("id") int id) {
		db.deleteShoppingCard(id);
		
		return Response.ok("deleted", MediaType.TEXT_HTML).build();
	}
	
	@POST
	@Path("card/add")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addShoppingcard(ShoppingCard card) {
		db.addShoppingCard(card);
		
		return Response.ok("added", MediaType.TEXT_HTML).build();
	}
	
	@PUT
	@Path("card/state/{id}/{state}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response changeState(@PathParam("id") int id, @PathParam("state") String state) {
		db.changeStateShoppingCard(id, state);
		
		return Response.ok("updated", MediaType.TEXT_HTML).build();
	}
	
	@GET
	@Path("card/user/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<ShoppingCard> usersCards(@PathParam("id") int id){
		return db.UsersShoppingCard(id);
	}
	
	
}
