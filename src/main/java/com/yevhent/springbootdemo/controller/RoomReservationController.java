package com.yevhent.springbootdemo.controller;

import com.yevhent.springbootdemo.model.ReservationModel;
import com.yevhent.springbootdemo.service.ReservationService;
import com.yevhent.springbootdemo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class RoomReservationController {

    private final ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getRoomReservations(@RequestParam Optional<String> targetDate, @RequestParam Optional<String> roomNumber, Model model) {
        List<ReservationModel> reservationModels;
        if (targetDate.isPresent() && roomNumber.isPresent()) {
            reservationModels = reservationService.getReservation(DateUtil.convert(targetDate.get()), roomNumber.get()).stream().toList();
        } else if (targetDate.isPresent()) {
            reservationModels = reservationService.getReservations(DateUtil.convert(targetDate.get()));
        } else {
            reservationModels = reservationService.getReservations();
        }
        model.addAttribute("currentDateTime", DateUtil.dateTimeNow());
        model.addAttribute("roomReservations", reservationModels);
        return "room_reservations";
    }
}