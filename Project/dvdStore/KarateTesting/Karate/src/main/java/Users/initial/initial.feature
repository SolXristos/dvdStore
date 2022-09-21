Feature: Initial State
  
  
  Background:
    * url 'http://127.0.0.1:8000'

  
    
  Scenario: No users exists
    Given path 'users','rest','users'
    And header Accept = 'application/json'
    When method get
    Then match [200,204] contains responseStatus
    And match response == ''
    
  Scenario: Get non-existing users
    Given path 'users','rest','users'
    When method get
    Then status 404
    
  Scenario: Follow wrong path
  	Given path 'users','rest','dvd'
  	When method get
  	Then status 404
  	
  Scenario: Follow second wrong path
  	Given path 'users','rest','users','dvd'
  	When method get
  	Then status 404