package com.yevhent.springbootdemo.startup;

import com.yevhent.springbootdemo.data.Room;
import com.yevhent.springbootdemo.data.RoomRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupHandler implements ApplicationListener<ApplicationReadyEvent> {

    private final RoomRepository roomRepository;

    public AppStartupHandler(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Iterable<Room> rooms = roomRepository.findAll();
        System.out.println("Existing rooms:");
        for (Room room : rooms) {
            System.out.println(room.toString());
        }
    }
}