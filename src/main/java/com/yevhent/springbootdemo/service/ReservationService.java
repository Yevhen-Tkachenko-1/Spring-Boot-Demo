package com.yevhent.springbootdemo.service;

import com.yevhent.springbootdemo.data.Guest;
import com.yevhent.springbootdemo.data.GuestRepository;
import com.yevhent.springbootdemo.data.Reservation;
import com.yevhent.springbootdemo.data.ReservationRepository;
import com.yevhent.springbootdemo.data.Room;
import com.yevhent.springbootdemo.data.RoomRepository;
import com.yevhent.springbootdemo.model.ReservationModel;
import com.yevhent.springbootdemo.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Reservation> getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getReservations() {
        return CollectionUtil.toList(reservationRepository.findAll());
    }

    public List<ReservationModel> getReservationModels() {
        return CollectionUtil.toList(reservationRepository.findAll(), this::buildReservationModel);
    }

    public List<ReservationModel> getReservationModels(Date date) {
        List<Reservation> reservations = reservationRepository.findByTargetDate(date);
        return reservations.stream().map(this::buildReservationModel).collect(Collectors.toList());
    }

    public Optional<ReservationModel> getReservationModel(Date date, String roomNumber) {
        Optional<Room> room = roomRepository.findByNumber(roomNumber);
        Optional<Reservation> reservation = room.flatMap(r -> reservationRepository.findByTargetDateAndRoomId(date, r.getId()));
        return reservation.map(this::buildReservationModel);
    }

    public Optional<Reservation> createReservation(ReservationModel reservationModel) {
        Room room = roomRepository.findByNumber(reservationModel.getRoomNumber())
                .orElseThrow(() -> new IllegalArgumentException("Room is not present: " + reservationModel.getRoomNumber()));
        boolean roomIsAlreadyReserved = reservationRepository.existsByTargetDateAndRoomId(reservationModel.getTargetDate(), room.getId());
        if (roomIsAlreadyReserved) {
            return Optional.empty();
        }
        Guest guest = guestRepository.findByEmail(reservationModel.getGuestEmail()).orElseThrow();
        Reservation reservation = new Reservation(room.getId(), guest.getId(), reservationModel.getTargetDate());
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