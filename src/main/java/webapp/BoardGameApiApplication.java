package webapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class BoardGameApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BoardGameApiApplication.class, args);
        // SpringApplication.exit(SpringApplication.run(BoardGameApiApplication.class, args));

    }

    @Override
    public void run(String... args) throws Exception {
    }
}

