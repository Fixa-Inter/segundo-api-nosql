package org.example.segundoapinosql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SegundoApiNosqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SegundoApiNosqlApplication.class, args);
    }

}
