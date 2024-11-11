package yevhent.demo.spring.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OutputService {

    private final GreetingService greetingService;
    private final TimeService timeService;
    @Value("${app.name}")
    private String name;
    @Value("#{new String(environment['spring.profiles.active'])}")
    private String activeProfileName;

    @Autowired
    public OutputService(GreetingService greetingService, TimeService timeService) {
        System.out.println("OutputService creation ...");
        this.greetingService = greetingService;
        this.timeService = timeService;
    }

    public void generateOutput() {
        try {
            String output = String.format("[%s] [%s] [%s]", timeService.getCurrentTime(), activeProfileName, greetingService.getGreeting(name));
            System.out.println(output);
        } catch (Exception e) {
        }
    }
}
