package com.mycompany.model;

import java.util.Date;

public class Person {
    private String name;
    private PhoneNumber phoneNumber;
    private Date date;

    public Person(String name, PhoneNumber phoneNumber, Date date) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", date=" + date +
                '}';
    }
}
