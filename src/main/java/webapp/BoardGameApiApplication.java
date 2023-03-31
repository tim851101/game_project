package webapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


@SpringBootApplication
@ComponentScan(
    basePackages = {"core.config", "webapp"},
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.REGEX,
        pattern = "webapp.*.pojo.*"
    ))
public class BoardGameApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BoardGameApiApplication.class, args);
//         SpringApplication.exit(SpringApplication.run(BoardGameApiApplication.class, args));

    }

    @Override
    public void run(String... args) throws Exception {

    }
}

