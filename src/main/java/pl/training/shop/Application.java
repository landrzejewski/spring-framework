package pl.training.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// java -jar -Dspring.profiles.active=dev  shop-1.0-SNAPSHOT.jar
// ./mvnw -Pnative spring-boot:build-image

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
