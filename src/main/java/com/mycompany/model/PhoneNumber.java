package com.mycompany.model;

public class PhoneNumber {

    private final String number;

//    private final Num number;

    private PhoneNumber(String number) {
        this.number = number;
    }

    public static PhoneNumber of(String number) {
        return new PhoneNumber(number);
    }


    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "number='" + number + '\'' +
                '}';
    }
}
