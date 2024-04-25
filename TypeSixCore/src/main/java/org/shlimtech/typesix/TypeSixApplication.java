package org.shlimtech.typesix;

import org.shlimtech.typesixbusinesslogic.EnableTypeSixBusinessLogic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTypeSixBusinessLogic
public class TypeSixApplication {

    public static void main(String[] args) {
        SpringApplication.run(TypeSixApplication.class, args);
    }

}
