package yevhent.demo.spring.core.service;

import com.yevhent.springdemo.aspect.Loggable;
import com.yevhent.springdemo.aspect.TimeTrackable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @Value("${app.greeting}")
    private String greeting;
    @Value("#{environment['spring.profiles.active'] == 'prod'}")
    private boolean isFormat;
    private boolean isError = false;

    public GreetingService() {
        System.out.println("GreetingService creation ...");
    }

    @Loggable
    @TimeTrackable
    public String getGreeting(String name) {
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        isError = !isError;
        if(isError){
            throw new RuntimeException("Unable to get Greeting");
        }
        return isFormat ? String.format("\"%s %s!\"", greeting, name) : greeting + " " + name;
    }
}
