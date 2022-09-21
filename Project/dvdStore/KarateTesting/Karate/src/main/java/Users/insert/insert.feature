Feature: Add a user
  
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

  Scenario: Adding empty user
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'user','xxxx','card', 'samos','xxxxxx', 'xxxx','xxx'
    And request {}
    And header Content-Type = 'application/json'
    When method put
    Then status 400
    
  Scenario: Adding user with wrong content type
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    * def User = call randomUser 'xxx' 
    Given path 'user','admin','xxx' 
    And request User
    And header Content-Type = 'text/html'
    When method put
    Then status 415
    
  Scenario: Adding correct User with no authorization
    * def User = call randomUser 'xxx' 
    Given path 'User','admin','xxx' 
    And request User
    And header Content-Type = 'application/json'
    When method put
    Then status 401
    
  Scenario: Adding correct user with wrong id path variable
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    * def User = call randomUser 'xxx' 
    Given path 'user','admin','xxx' 
    And request User
    And header Content-Type = 'application/json'
    When method put
    Then status 400
    
    
  Scenario: User added exists
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'user'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And match header Content-Type == 'application/json'
    And match response.id == '1'
    
  Scenario: Only one user exists
    Given header Accept = 'application/json'
    When method get
    Then status 200
    And match header Content-Type == 'application/json'
    And match response == '#[1]'
    And match response[0].id == '1'
    
  Scenario: user with id xxxx does not exist
    Given path 'user', 'xxxx'
    And header Accept = 'application/json'
    When method get
    Then status 404
    
 
    
  Scenario: Get all added users
    Given header Accept = 'application/json'
    When method get
    Then status 200
    And match response == '#[1]'
    And match response[*].id  