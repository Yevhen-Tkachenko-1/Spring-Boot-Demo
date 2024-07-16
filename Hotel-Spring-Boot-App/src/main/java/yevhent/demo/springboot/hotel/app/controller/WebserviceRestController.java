package yevhent.demo.springboot.hotel.app.controller;

import yevhent.demo.springboot.hotel.app.data.Guest;
import yevhent.demo.springboot.hotel.app.data.Reservation;
import yevhent.demo.springboot.hotel.app.model.ReservationModel;
import yevhent.demo.springboot.hotel.app.service.GuestBaseService;
import yevhent.demo.springboot.hotel.app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WebserviceRestController {

    private final GuestBaseService guestBaseService;
    private final ReservationService reservationService;

    @GetMapping("/guestbase")
    public List<Guest> getGuests(@RequestParam(name = "id")  Optional<Long> anyId) {
        List<Guest> guests = anyId.map(id -> guestBaseService.getGuest(id).stream().toList()).orElseGet(guestBaseService::getGuests);
        return guests;
    }

    @PostMapping("/guestbase")
    public Guest addGuest(@RequestBody Guest guest) {
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