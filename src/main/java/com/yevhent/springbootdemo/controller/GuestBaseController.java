package com.yevhent.springbootdemo.controller;

import com.yevhent.springbootdemo.model.GuestModel;
import com.yevhent.springbootdemo.service.GuestBaseService;
import com.yevhent.springbootdemo.util.DateUtil;
import com.yevhent.springbootdemo.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/guestbase")
@RequiredArgsConstructor
public class GuestBaseController {

    private final GuestBaseService guestBaseService;

    @GetMapping({"", "/{email}"})
    public ModelAndView getGuestBase(@PathVariable Optional<String> email) {
        ModelAndView modelAndView = new ModelAndView("guest_base");
        List<GuestModel> guestBase = email.map(e -> guestBaseService.getGuestModel(e).stream().toList())
                .orElseGet(guestBaseService::getGuestModels);
        modelAndView.addObject("currentDateTime", DateUtil.dateTimeNow());
        modelAndView.addObject("guestBase", guestBase);
        return modelAndView;
    }

    @GetMapping("/name")
    public ModelAndView getGuest(@RequestParam String firstName, @RequestParam String lastName) {
        StringUtil.requireNotEmpty(firstName, lastName);
        ModelAndView modelAndView = new ModelAndView("guest_base");
        Optional<GuestModel> guest = guestBaseService.getGuestModel(firstName, lastName);
        modelAndView.addObject("currentDateTime", DateUtil.dateTimeNow());
        modelAndView.addObject("guestBase", guest.orElse(null));
        return modelAndView;
    }
}