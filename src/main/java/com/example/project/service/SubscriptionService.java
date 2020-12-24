package com.example.project.service;

import com.example.project.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import com.example.project.repository.SubscriptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    public List<Subscription> getAllSubs(){
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptionRepository.findAll().forEach(subscriptions::add);
        return subscriptions;
    }

    public ResponseEntity<Subscription> getSubsById(int id) {
        if(subscriptionRepository.findById(id).isPresent()){
            return new ResponseEntity<>(subscriptionRepository.findById(id).get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void create(@Validated Subscription subscription){
        subscriptionRepository.save(subscription);
    }

    public void update(int id, Subscription subscription){
        if(subscriptionRepository.findById(id).isPresent()) {
            Subscription updateSub = subscriptionRepository.findById(id).get();
            updateSub.setName(subscription.getName());
            updateSub.setPrice(subscription.getPrice());
            updateSub.setDate(subscription.getDate());
            updateSub.setAccount(subscription.getAccount());

            create(updateSub);
        }
    }

    public void delete(int id){
        subscriptionRepository.deleteById(id);
    }

}
