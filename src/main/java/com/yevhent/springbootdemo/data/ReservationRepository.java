package com.yevhent.springbootdemo.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findByTargetDate(Date targetDate);

    Optional<Reservation> findByTargetDateAndRoomId(Date targetDate, Long roomId);

    boolean existsByTargetDateAndRoomId(Date targetDate, Long roomId);
}