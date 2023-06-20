package com.yevhent.springbootdemo.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Iterable<Reservation> findAllByTargetDate(Date targetDate);

    boolean existsByTargetDateAndRoomId(Date targetDate, Long roomId);
}