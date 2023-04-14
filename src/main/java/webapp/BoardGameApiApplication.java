package webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import webapp.booking.service.SeatService;
import webapp.security.service.DatabaseService;


@SpringBootApplication
@EnableScheduling
@ComponentScan(
    basePackages = {"core.config", "webapp"},
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "webapp.*.pojo.*"
    ))
public class BoardGameApiApplication implements CommandLineRunner {

    @Autowired
    private SeatService seatService;

    private DatabaseService databaseService;
    public static void main(String[] args) {
        SpringApplication.run(BoardGameApiApplication.class, args);
//         SpringApplication.exit(SpringApplication.run(BoardGameApiApplication.class, args));
    }

    @Override
    public void run(String... args) throws Exception {
        // initial seat vacancy into redis
        seatService.initialSeat();
        databaseService.initializeDatabase();
    }
}

