package com.yevhent.springbootdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuestModel {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Override
    public String toString() {
        return "GuestModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}