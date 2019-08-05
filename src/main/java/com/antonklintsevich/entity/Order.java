package com.antonklintsevich.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity(name = "Genre")
@Table(name = "genre")
public class Order {
    @Id
    @Column(name="order_id")
    @GeneratedValue
private Long id;
private Long userid;
private Date orderdate;
private Long bookid;
private int price;
}
