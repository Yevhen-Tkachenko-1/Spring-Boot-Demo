package com.yevhent.springbootdemo.startup;

import com.yevhent.springbootdemo.data.Guest;
import com.yevhent.springbootdemo.data.GuestRepository;
import com.yevhent.springbootdemo.data.Reservation;
import com.yevhent.springbootdemo.data.ReservationRepository;
import com.yevhent.springbootdemo.data.Room;
import com.yevhent.springbootdemo.data.RoomRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AppStartupHandler implements ApplicationListener<ApplicationReadyEvent> {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public AppStartupHandler(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Iterable<Room> rooms = roomRepository.findAll();
        System.out.println("Existing rooms:");
        for (Room room : rooms) {
            System.out.println(room);
        }
        Iterable<Guest> guests = guestRepository.findAll();
        System.out.println("Existing guests:");
        for (Guest guest : guests) {
            System.out.println(guest);
        }
        Iterable<Reservation> reservations = reservationRepository.findAll();
        System.out.println("Existing reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }

        try {
            Date date = new SimpleDateFormat("yyyy-dd-MM").parse("2022-01-01");
            Iterable<Reservation> dateReservations = reservationRepository.findAllByTargetDate(date);
            System.out.println("Reservations for " + date + ":");
            for (Reservation reservation : dateReservations) {
                System.out.println(reservation);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}