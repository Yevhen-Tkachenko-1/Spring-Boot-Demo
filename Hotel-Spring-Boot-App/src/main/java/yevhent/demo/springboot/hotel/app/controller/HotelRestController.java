package yevhent.demo.springboot.hotel.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yevhent.demo.springboot.hotel.app.data.Guest;
import yevhent.demo.springboot.hotel.app.data.Reservation;
import yevhent.demo.springboot.hotel.app.model.ReservationModel;
import yevhent.demo.springboot.hotel.app.service.GuestBaseService;
import yevhent.demo.springboot.hotel.app.service.ReservationService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HotelRestController {

    private final GuestBaseService guestBaseService;
    private final ReservationService reservationService;

    @GetMapping("/guestbase")
    public List<Guest> getGuests(@RequestParam(name = "id")  Optional<Long> anyId) {
        List<Guest> guests = anyId.map(id -> guestBaseService.getGuest(id).stream().toList()).orElseGet(guestBaseService::getGuests);
        return guests;
    }

    @GetMapping("/guest/{id}")
    public Guest getGuest(@PathVariable Long id) {
        return guestBaseService.findGuest(id);
    }

    @PostMapping("/guestbase")
    public Guest addGuest(@Valid @RequestBody Guest guest) {
        Objects.requireNonNull(guest);
        return guestBaseService.addGuest(guest)
                .orElseThrow(() -> new IllegalStateException("Unable to add " + guest));
    }

    @GetMapping("/reservations")
    public List<Reservation> getRoomReservations(@RequestParam(name = "id") Optional<Long> anyId) {
        List<Reservation> reservations = anyId.map(id -> reservationService.getReservation(id).stream().toList())
                .orElseGet(reservationService::getReservations);
        return reservations;
    }

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody ReservationModel reservationModel) {
        Objects.requireNonNull(reservationModel);
        return reservationService.createReservation(reservationModel)
                .orElseThrow(() -> new IllegalStateException(reservationModel + " is not available."));
    }
}