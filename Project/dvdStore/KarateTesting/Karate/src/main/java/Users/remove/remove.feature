Feature: Remove user functionality
 
  Background:
    * url 'http://127.0.0.1:8000/project/dvdStore/users'

  Scenario: Delete non-existing user
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'user','admin','xxxxx' 
    And request {}
    When method delete
    Then status 404
    
  Scenario: Delete existing user with no authorization
    Given path 'user','admin','1' 
    When method delete
    Then status 401
    
  Scenario: Correctly delete existing user
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'user','employee','1' 
    When method delete
    Then status 200
    
  Scenario: Get removed user 
    Given path 'user','1' 
    And header Accept = 'application/json'
    When method get
    Then status 404
    
  Scenario: Get remaining users
    Given header Accept = 'application/json'
    When method get
    Then status 200
    And match response == '#[3]'
    And match response[*].id 
    
  Scenario Outline: Correctly delete all existing users
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'users','admin',<id> 
    When method delete
    Then status 200
    
  