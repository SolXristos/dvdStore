Feature: Order search 
  
  Background:
     * url 'http://127.0.0.1:8000/project/dvdStore/orders'
    * def randomorder =
  		"""
  		function(arg) {
    		var Order = Java.type('com.project.dvdStore.orders');
    		var order = new Order();
    		order.setId(arg);
    		var oder = Math.floor(Math.random() * 10) + 1;
	    	order.setorderTitle("order" + orderTitle);
	    	var actor = Math.floor(Math.random() * 10) + 1;
	    	order.setActors("Actors" + actor);
	    	var director =Math.floor(Math.random() * 10) + 1;
	    	order.setDirector("Director" + director);
	    	var date = Math.floor(Math.random() * 10) + 1;
	    	order.setDate("Date" + date);
	    	var lang = Math.floor(Math.random() * 10) + 1;
	    	order.setLanguage("Language" + lang);
	    	var subs = Math.floor(Math.random() * 10) + 1;
	    	order.setSubs("Subtitles " + subs);
	    	var kind = Math.floor(Math.random() * 10) + 1;
	    	order.setKind("Kind" + kind);
	    	var price = Math.floor(Math.random() * 10) + 1;
	    	var pieces = Math.floor(Math.random() * 10) + 1;
    		return karate.toJson(order,true);  
  		}
  		"""
  		
  		Scenario Outline: Search order based on id 
  	Given header Accept = 'application/json'
  	And param id = <id>
    When method get
    Then status 200
    And match response == <num>
    And match response[*].id contains only <id>
    
    Scenario Outline: Search order based on actors 
  	Given header Accept = 'application/json'
  	And param actor = <actors>
    When method get
    Then status 200
    And match response == <num>
    And match response[*].id contains only <actors>
    
    Scenario Outline: Search order based on kind 
  	Given header Accept = 'application/json'
  	And param kind = <kind>
    When method get
    Then status 200
    And match response == <num>
    And match response[*].id contains only <kind>
    
    Scenario Outline: Search order based on director 
  	Given header Accept = 'application/json'
  	And param director = <director>
    When method get
    Then status 200
    And match response == <num>
    And match response[*].id contains only <director>
    
    Scenario Outline: Search order based on language 
  	Given header Accept = 'application/json'
  	And param lang = <language>
    When method get
    Then status 200
    And match response == <num>
    And match response[*].id contains only <lang>