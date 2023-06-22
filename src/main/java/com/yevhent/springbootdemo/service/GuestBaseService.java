package com.yevhent.springbootdemo.service;

import com.yevhent.springbootdemo.data.Guest;
import com.yevhent.springbootdemo.data.GuestRepository;
import com.yevhent.springbootdemo.model.GuestModel;
import com.yevhent.springbootdemo.util.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestBaseService {

    private final GuestRepository guestRepository;

    public List<GuestModel> getGuestBase() {
        return CollectionUtil.toList(guestRepository.findAll(), this::buildGuestModel);
    }

    public Optional<GuestModel> getGuest(String email) {
        return guestRepository.findByEmail(email).map(this::buildGuestModel);
    }

    public Optional<GuestModel> getGuest(String firstName, String lastName) {
        return guestRepository.findByFirstNameAndLastName(firstName, lastName).map(this::buildGuestModel);
    }

    private GuestModel buildGuestModel(Guest guest) {
        return new GuestModel(guest.getFirstName(), guest.getLastName(), guest.getPhoneNumber());
    }
}