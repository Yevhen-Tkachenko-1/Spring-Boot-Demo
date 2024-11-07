package yevhent.demo.springboot.hotel.app.service;

import yevhent.demo.springboot.hotel.app.data.Guest;
import yevhent.demo.springboot.hotel.app.data.GuestRepository;
import yevhent.demo.springboot.hotel.app.exception.ResourceNotFoundException;
import yevhent.demo.springboot.hotel.app.model.GuestModel;
import yevhent.demo.springboot.hotel.app.util.CollectionUtil;
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

    public Guest findGuest(Long id) {
        return guestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Guest with id = " + id));
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