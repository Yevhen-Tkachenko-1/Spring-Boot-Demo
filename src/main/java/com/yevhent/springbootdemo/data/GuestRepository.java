package com.yevhent.springbootdemo.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface GuestRepository extends CrudRepository<Guest, Long> {
}