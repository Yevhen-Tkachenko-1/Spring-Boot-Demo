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

    public Optional<Guest> getGuest(Long id) {
        return guestRepository.findById(id);
    }

    public List<Guest> getGuests() {
        return CollectionUtil.toList(guestRepository.findAll());
    }

    public List<GuestModel> getGuestModels() {
        return CollectionUtil.toList(guestRepository.findAll(), this::buildGuestModel);
    }

    public Optional<GuestModel> getGuestModel(String email) {
        return guestRepository.findByEmail(email).map(this::buildGuestModel);
    }

    public Optional<Guest> addGuest(Guest guest) {
        return guestRepository.findByEmail(guest.getEmail()).or(() -> Optional.of(guestRepository.save(guest)));
    }

    public Optional<GuestModel> getGuestModel(String firstName, String lastName) {
        return guestRepository.findByFirstNameAndLastName(firstName, lastName).map(this::buildGuestModel);
    }

    private GuestModel buildGuestModel(Guest guest) {
        return new GuestModel(guest.getFirstName(), guest.getLastName(), guest.getPhoneNumber());
    }
}