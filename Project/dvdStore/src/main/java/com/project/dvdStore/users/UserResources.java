package com.project.dvdStore.users;

import java.util.ArrayList;

import dbHandler.DBHandler;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users")
public class UserResources {
	
	DBHandler db = new DBHandler();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<User> getUsers(){
		return db.getAllUsers();
	}
	

	@GET
	@Path("/user/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public User getUser(@PathParam("id") int id){
		return db.getUser(id);
	}
	
	@POST
	@Path("/user/signin")
	@Consumes(MediaType.APPLICATION_XML)
	public Response signIn(User user) {
		boolean answer = db.addUser(user);
		
		if(answer) {
			return Response.ok("Sign in succesfully", MediaType.TEXT_HTML).build();
		}else {
			return Response.ok("Username already exists",MediaType.TEXT_HTML).build();
		}
	}
	
	@GET
	@Path("/user/login/{username}/{password}")
	@Produces(MediaType.APPLICATION_XML)
	public Response login(@PathParam("username") String username, @PathParam("password") String password) {
		User u = db.logUser(username, password);
		
		if(u !=null) {
			return Response.ok("Loged in", MediaType.TEXT_HTML).build();
		}else {
			return Response.ok("Wrong password or username", MediaType.TEXT_HTML).build();
		}
		
	}

	@GET
	@Path("user/password/{id}/{password}/{newPassword}")
	@Produces(MediaType.APPLICATION_XML)
	public Response changePassword(@PathParam("id") int id,@PathParam("password") String password, @PathParam("newPassword") String newPassword) {
		boolean answer = db.changePassword(id, password, newPassword);
		if(answer) {
			return Response.ok("Password changed").build();
		}else {
			return Response.ok("There was an error!!").build();
		}
	}
	
	@GET
	@Path("user/delete/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response deleteUser(@PathParam("id") int id) {
		boolean answer = db.deleteUser(id);
		
		if(answer) {
			return Response.ok("Deleted").build();
		}else {
			return Response.ok("there was an error").build();
		}
	}
	
	@GET
	@Path("user/test")
	@Consumes(MediaType.APPLICATION_XML)
	public int test1() {
		int test  = db.statsOrdersActive();
		return test;
	}
	
	//----STATS----
	@GET
	@Path("stats/active/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response active(@PathParam("id") int id) {
		
		return Response.ok("You have " + db.statsOrdersActiveUser(id) +" active order(s)").build();
	}
	
	@GET
	@Path("stats/canceled/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response canceled(@PathParam("id") int id) {
		
		return Response.ok("You have " + db.statsOrdersCanceledUser(id) +" canceled order(s)").build();
	}
	
	@GET
	@Path("stats/completed/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response completed(@PathParam("id") int id) {
		
		return Response.ok("You have " + db.statsOrdersCompletedUser(id) +" completed order(s)").build();
	}
	
}
