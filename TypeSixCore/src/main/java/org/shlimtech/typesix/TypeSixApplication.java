package org.shlimtech.typesix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.shlimtech.typesixdatabasecommon")
@ComponentScan(basePackages = "org.shlimtech.typesixdatabasecommon")
@EntityScan(basePackages = "org.shlimtech.typesixdatabasecommon")
public class TypeSixApplication {

    public static void main(String[] args) {
        SpringApplication.run(TypeSixApplication.class, args);
    }

}
