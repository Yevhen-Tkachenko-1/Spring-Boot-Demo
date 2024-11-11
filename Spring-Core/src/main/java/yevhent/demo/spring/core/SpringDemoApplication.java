package yevhent.demo.spring.core;

import yevhent.demo.spring.core.config.ApplicationConfig;
import yevhent.demo.spring.core.service.OutputService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDemoApplication {

    public static void main(String[] args) throws Exception {
        System.out.println("main() method start");

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        System.out.println("ApplicationContext created");
        OutputService outputService = context.getBean(OutputService.class);
        System.out.println("OutputService instance is available");

        for (int i = 0; i < 3; i++) {
            outputService.generateOutput();
            Thread.sleep(1000);
        }
        System.out.println("main() method end");
    }
}
