Feature: subscription feature
  Scenario: A user gets all subscriptions
#    Given the following subscriptions
#      | name    | price | date | account   |
#      | Netflix | 5.99  | 9    | Santander |
#      | Disney+ | 7.99  | 10   | Amex      |
#      | Spotify | 9.99  | 15   | Lloyds    |
    When a user requests all subscriptions
    Then all the subscriptions are returned
    And the status code is 200

    Scenario: A user gets a specific subscription
      When a user requests a subscription with the id 3
      Then the subscription is returned
      And the status code is 200

    Scenario: A user posts a new subscription
      When a user posts a new subscription iCloud with price 0.79 date 20 account Amex
      Then it is in the database
      And it has an id
      And the status code is 201

    Scenario: A user deletes a subscription
      When a user deletes a subscription with the id 4
      Then it is removed from the database

    Scenario: A user updates a subscription
      When a user updates a subscription with the id 1 to name Amazon Prime price 7.99 date 19 account Lloyds
      Then the subscription body is updated
