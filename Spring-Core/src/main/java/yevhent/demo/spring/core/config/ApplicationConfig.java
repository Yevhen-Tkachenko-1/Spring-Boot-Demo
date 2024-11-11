package yevhent.demo.spring.core.config;

import yevhent.demo.spring.core.service.TimeService;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "yevhent.demo.spring.core")
@EnableAspectJAutoProxy
public class ApplicationConfig {

    @Profile("dev")
    public TimeService timeService24(){
        System.out.println("TimeService24 Bean creation ...");
        return new TimeService(true);
    }

    @Bean
    @Profile("!dev")
    public TimeService timeService12(){
        System.out.println("TimeService12 Bean creation ...");
        return new TimeService(false);
    }
}
