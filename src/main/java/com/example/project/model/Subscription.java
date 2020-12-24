package com.example.project.model;

import javax.persistence.*;

@Entity
@Table
public class Subscription {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id", unique = true, nullable = false)
    private Integer id;
    @Column
    private String name;
    @Column
    private Double price;
    @Column
    private Integer date;
    @Column
    private String account;

    public Subscription(){}

    public Subscription(String name, double price, int date, String account) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
