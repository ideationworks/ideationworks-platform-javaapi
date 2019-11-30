package ideationworks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = { "springblack", "ideationworks" })
@EnableJpaRepositories(basePackages = { "springblack", "ideationworks" })
@ComponentScan(basePackages = { "springblack", "ideationworks" })
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Asdf");

    }

}
