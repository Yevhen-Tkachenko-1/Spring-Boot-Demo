package com.yevhent.springbootdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ReservationModel {

    @NonNull
    private Date targetDate;
    private String roomNumber;
    private String guestEmail;

    @Override
    public String toString() {
        return "ReservationModel{" +
                "targetDate=" + targetDate +
                ", roomNumber='" + roomNumber + '\'' +
                ", guestEmail='" + guestEmail + '\'' +
                '}';
    }
}