Feature: CRUD functionality Subscriptions

  Background:
    * url 'http://localhost:8085/'

  Scenario: Retrieve all subscriptions
    Given path 'subscriptions'
    When method get
    Then status 200

  Scenario: Retrieve one subscription
    Given path 'subscriptions/1'
    When method get
    Then status 200

  Scenario: Add a new subscription
    Given path 'subscriptions'
    And request {name: 'iCloud', price: 0.79, date: 24, account: 'Lloyds'}
    When method post
    Then status 201

  Scenario: Delete one subscription
    Given path 'subscriptions/4'
    When method delete
    Then status 204

  Scenario: Update a subscription
    Given path 'subscriptions/1'
    And request {name: 'Amazon Prime', price: 7.99, date: 24, account: 'Amex'}
    When method put
    Then status 200