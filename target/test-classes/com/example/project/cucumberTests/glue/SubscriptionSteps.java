package com.example.project.cucumberTests.glue;

import com.example.project.model.Subscription;
import com.example.project.repository.SubscriptionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SubscriptionSteps {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Subscription> expectedSubs;

    private List<Subscription> actualSubs;

    private int actualStatus;

    private Subscription beforeSub;

    private Subscription afterSub;

    @Before
    public void setup(){
        expectedSubs = new ArrayList<>();
        actualSubs = new ArrayList<>();
        subscriptionRepository.findAll().forEach(expectedSubs::add);
    }

//    @Given("^the following subscriptions$")
//    public void givenTheFollowingSubscriptions(final List<Subscription> subscriptions){
//        subscriptionRepository.findAll().forEach(expectedSubs::add);
//        subscriptionRepository.saveAll(subscriptions);
//    }

    @When("^a user requests all subscriptions$")
    public void whenUserRequestsAllSubscriptions() throws JsonProcessingException {
        actualSubs.addAll(Arrays.asList(
                objectMapper.readValue(
                        testRestTemplate.getForEntity("/subscriptions", String.class)
                                .getBody(), Subscription[].class)));

        actualStatus = testRestTemplate.getForEntity("/subscriptions", String.class).getStatusCodeValue();
    }

    @When("^a user requests a subscription with the id ([0-9])$")
    public void whenUserRequestsASubscription(final int id) throws JsonProcessingException {
        beforeSub = subscriptionRepository.findById(id).get();
        afterSub = objectMapper.readValue(testRestTemplate.getForEntity("/subscriptions/"+id, String.class).getBody(), Subscription.class);
        actualStatus = testRestTemplate.getForEntity("/subscriptions/"+id, String.class).getStatusCodeValue();
    }

    @When("^a user posts a new subscription (.*) with price ([0-9]+.[0-9]+) date ([0-9]{1,2}) account (.*)$")
    public void whenUserPostsANewSubscription(final String name, final double price, final int date, final String account)  {
        final Subscription expected = new Subscription(name, price, date, account);
        expectedSubs.add(expected);
        actualStatus = testRestTemplate.postForEntity("/subscriptions", expected, Subscription.class).getStatusCodeValue();
    }

    @When("^a user deletes a subscription with the id ([0-9])$")
    public void whenUserDeletesASubscription(final int id){
        beforeSub = subscriptionRepository.findById(id).get();
        testRestTemplate.delete("/subscriptions/", +id);
        subscriptionRepository.deleteById(id);
    }

    @When("^a user updates a subscription with the id ([0-9]) to name (.*) price ([0-9]+.[0-9]+) date ([0-9]{1,2}) account (.*)$")
    public void whenUserUpdatesASubscription(final int id, final String name, final double price, final int date, final String account){
        beforeSub = subscriptionRepository.findById(id).get();
        afterSub = subscriptionRepository.findById(id).get();
        afterSub.setName(name);
        afterSub.setPrice(price);
        afterSub.setDate(date);
        afterSub.setAccount(account);
        subscriptionRepository.save(afterSub);
    }

    @Then("^all the subscriptions are returned$")
    public void thenAllTheSubscriptionsAreReturned(){
        validateSubs();
    }

    @Then("^the subscription is returned$")
    public void thenTheSubscriptionIsReturned(){
        validateSub(beforeSub, afterSub);
    }

    @Then("^it is in the database$")
    public void thenItIsInTheDatabase(){
       subscriptionRepository.findAll().forEach(actualSubs::add);
       validateSubs();
    }

    @Then("^it is removed from the database$")
    public void thenItIsRemovedFromTheDatabase(){
        Assertions.assertFalse(subscriptionRepository.existsById(beforeSub.getId()));
    }

    @Then("^the subscription body is updated$")
    public void thenTheSubscriptionBodyIsUpdated(){
        Assertions.assertNotEquals(beforeSub, afterSub);
    }

    @And("^it has an id$")
    public void andItHasAnId(){
        Assertions.assertNotNull(actualSubs.get(0).getId());
    }

    @And("^the status code is ([0-9]{3})$")
    public void andTheStatusCodeIs(final int status){
        checkStatus(status);
    }

    public void checkStatus(int expectedStatus){
        Assertions.assertEquals(actualStatus, expectedStatus);
    }
    private void validateSubs(){
        Assertions.assertEquals(expectedSubs.size(), actualSubs.size());
        IntStream.range(0, actualSubs.size())
                .forEach(index -> validateSub(expectedSubs.get(index), actualSubs.get(index)));
    }

    private void validateSub(final Subscription expected, final Subscription actual) {
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(expected.getDate(), actual.getDate());
        Assertions.assertEquals(expected.getAccount(), actual.getAccount());
    }
}
