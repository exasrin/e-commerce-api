package com.enigma.challenge_tokonyadia_api.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "m_store")
public class Store {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "siup_number", nullable = false, unique = true)
    private String siupNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number", unique = true)
    private  String phoneNumber;

    public Store(String id, String siupNumber, String name, String address, String phoneNumber) {
        this.id = id;
        this.siupNumber = siupNumber;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Store() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiupNumber() {
        return siupNumber;
    }

    public void setSiupNumber(String siupNumber) {
        this.siupNumber = siupNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
