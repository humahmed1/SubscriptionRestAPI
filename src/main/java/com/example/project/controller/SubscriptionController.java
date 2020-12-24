package com.example.project.controller;

import com.example.project.model.Subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.project.service.SubscriptionService;

import java.net.URI;
import java.util.List;

@RestController
public class SubscriptionController {

    @Autowired
    SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    private List<Subscription> getAllSubs() {
        return subscriptionService.getAllSubs();
    }

    @GetMapping("/subscriptions/{id}")
    private ResponseEntity<Subscription> getSubscription(@PathVariable("id") int id) {
        return subscriptionService.getSubsById(id);
    }

    @DeleteMapping("/subscriptions/{id}")
    private ResponseEntity<Subscription> deleteSubscription(@PathVariable("id") int id) {
        if (subscriptionService.getSubsById(id).getStatusCode().is2xxSuccessful()) {
            subscriptionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/subscriptions")
    private ResponseEntity<Integer> createSubscription(@Validated @RequestBody Subscription subscription) {
        subscriptionService.create(subscription);
        URI location = URI.create("/subscription/" + subscription.getId().toString());
        HttpHeaders header = new HttpHeaders();
        header.setLocation(location);
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }

    @PutMapping("/subscriptions/{id}")
    private ResponseEntity<Subscription> updateSubscription(@PathVariable("id") int id, @RequestBody Subscription subscription) {
        if (subscriptionService.getSubsById(id).getStatusCode().is2xxSuccessful()) {
            subscriptionService.update(id, subscription);
            return subscriptionService.getSubsById(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
