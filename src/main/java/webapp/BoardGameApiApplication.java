package webapp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import webapp.booking.pojo.OpenHour;
import webapp.booking.repository.OpenHourRepository;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class BoardGameApiApplication implements CommandLineRunner {

    @Autowired
    private OpenHourRepository openHourRepository;

    public static void main(String[] args) {
        SpringApplication.run(BoardGameApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Testing the findByWeek method
        Integer week = 3;
        OpenHour openHour = openHourRepository.findByWeek(week);
        System.out.println("Open hour for week " + week + ": " + openHour);
//        SpringApplication.exit(SpringApplication.run(BoardGameApiApplication.class, args));
    }
}

