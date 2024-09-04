package pl.training.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// java -jar -Dspring.profiles.active=dev  shop-1.0-SNAPSHOT.jar

@Log
@SpringBootApplication
@RequiredArgsConstructor
public class Application  {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
