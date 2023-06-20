package com.yevhent.springbootdemo.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface GuestRepository extends CrudRepository<Guest, Long> {

    Optional<Guest> findByEmail(String email);
}