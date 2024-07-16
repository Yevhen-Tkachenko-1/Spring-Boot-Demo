package yevhent.demo.springboot.hotel.app.startup;

import yevhent.demo.springboot.hotel.app.data.Guest;
import yevhent.demo.springboot.hotel.app.data.GuestRepository;
import yevhent.demo.springboot.hotel.app.data.Reservation;
import yevhent.demo.springboot.hotel.app.data.ReservationRepository;
import yevhent.demo.springboot.hotel.app.data.Room;
import yevhent.demo.springboot.hotel.app.data.RoomRepository;
import yevhent.demo.springboot.hotel.app.model.ReservationModel;
import yevhent.demo.springboot.hotel.app.service.ReservationService;
import yevhent.demo.springboot.hotel.app.util.DateUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class AppStartupHandler implements ApplicationListener<ApplicationReadyEvent> {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    private final ReservationService reservationService;

    public AppStartupHandler(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository, ReservationService reservationService) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Iterable<Room> rooms = roomRepository.findAll();
        System.out.println("Existing rooms:");
        for (Room room : rooms) {
            System.out.println(room);
        }
        Optional<Room> roomById = roomRepository.findById(1L);
        System.out.println("Room by id=1:");
        System.out.println(roomById.get());
        Optional<Room> roomByNumber = roomRepository.findByNumber("P1");
        System.out.println("Room by number=P1:");
        System.out.println(roomByNumber.get());

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

        Date date = DateUtil.convert("2022-01-01");
        List<Reservation> dateReservations = reservationRepository.findByTargetDate(date);
        System.out.println("Reservations for " + date + ":");
        for (Reservation reservation : dateReservations) {
            System.out.println(reservation);
        }

        List<ReservationModel> reservationModels = reservationService.getReservationModels(date);
        System.out.println("Reservation Models for " + date + ":");
        for (ReservationModel reservation : reservationModels) {
            System.out.println(reservation);
        }

        ReservationModel reservationModel = new ReservationModel(date, "O1", "radams1v@xinhuanet.com");
        Optional<Reservation> createdReservation = reservationService.createReservation(reservationModel);
        System.out.println("Created Reservation for " + date + ":");
        createdReservation.ifPresent(System.out::println);
        Optional<Reservation> notCreatedReservation = reservationService.createReservation(reservationModel);
        System.out.println("Not Created Reservation for " + date + ":");
        if (notCreatedReservation.isEmpty()) {
            System.out.println(createdReservation.get());
        }
    }
}