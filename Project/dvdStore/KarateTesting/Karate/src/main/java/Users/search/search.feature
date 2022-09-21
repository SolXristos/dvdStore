Feature: Users search 
  
  Background:
     * url 'http://127.0.0.1:8000/project/dvdStore/users'
    * def randomUser =
  		"""
  		function(arg) {
    		var User = Java.type('com.project.dvdStore.users');
    		var user = new User();
    		user.setId(arg);
    		var Username = Math.floor(Math.random() * 10) + 1;
	    	user.setusername("User" + Username);
	    	var pw = Math.floor(Math.random() * 10) + 1;
	    	user.setPassword("Password" + pw);
	    	var sc=Math.floor(Math.random() * 10) + 1;
	    	user.setShoppingCard("Shopping Card" + sc);
	    	var address = Math.floor(Math.random() * 10) + 1;
	    	user.setAddress("Address" + address);
	    	var cardnum = Math.floor(Math.random() * 10) + 1;
	    	user.setCardNum("Card Num" + cardnum);
	    	var card = Math.floor(Math.random() * 10) + 1;
	    	user.setCard("Exp Date" + card);
	    	var cardpass = Math.floor(Math.random() * 10) + 1;
	    	user.setCardPass("Card pass" + cardpass);
	    	var surname = Math.floor(Math.random() * 10) + 1;
	    	var lastname = Math.floor(Math.random() * 10) + 1;
	    	var Surname = "Surname" + surname;
	    	var Lastname = "Lastname" + lastname;
	    	const name = [Surname, Lastname];
	    	user.setName(name);
    		return karate.toJson(user,true);  
  		}
  		"""
   
   
    
  Scenario Outline: Search users based on id 
  	Given header Accept = 'application/json'
  	And param id = <id>
    When method get
    Then status 200
    And match response == <num>
    And match response[*].id contains only <id>
    
       
  Scenario Outline: Search users based on name 
  	Given header Accept = 'application/json'
  	And param name = <fullanme>
    When method get
    Then status 200
    And match response == <num>
    And match response[*].id contains only <fullname>
    