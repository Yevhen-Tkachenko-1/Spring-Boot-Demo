package com.yevhent.springbootdemo.service;

import com.yevhent.springbootdemo.data.Guest;
import com.yevhent.springbootdemo.data.GuestRepository;
import com.yevhent.springbootdemo.data.Reservation;
import com.yevhent.springbootdemo.data.ReservationRepository;
import com.yevhent.springbootdemo.data.Room;
import com.yevhent.springbootdemo.data.RoomRepository;
import com.yevhent.springbootdemo.model.ReservationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReservationService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationModel> getReservationsByDate(Date date) {
        Iterable<Reservation> reservations = reservationRepository.findAllByTargetDate(date);

        return StreamSupport.stream(reservations.spliterator(), false)
                .map(this::buildReservationModel).collect(Collectors.toList());
    }

    public Optional<Reservation> createReservation(ReservationModel reservationModel) {
        Optional<Room> rooms = roomRepository.findByNumber(reservationModel.getRoomNumber());
        Room requestedRoom = rooms.get();
        boolean roomIsAlreadyReserved = reservationRepository.existsByTargetDateAndRoomId(reservationModel.getTargetDate(), requestedRoom.getId());
        if(roomIsAlreadyReserved){
            return Optional.empty();
        }
        Guest guest = guestRepository.findByEmail(reservationModel.getGuestEmail()).orElseThrow();
        Reservation reservation = new Reservation(requestedRoom.getId(), guest.getId(), reservationModel.getTargetDate());
        return Optional.of(reservationRepository.save(reservation));
    }

    private ReservationModel buildReservationModel(Reservation reservation) {
        ReservationModel reservationModel = new ReservationModel(reservation.getTargetDate());
        Room room = roomRepository.findById(reservation.getRoomId()).orElseThrow();
        reservationModel.setRoomNumber(room.getNumber());
        Guest guest = guestRepository.findById(reservation.getGuestId()).orElseThrow();
        reservationModel.setGuestEmail(guest.getEmail());
        return reservationModel;
    }
}