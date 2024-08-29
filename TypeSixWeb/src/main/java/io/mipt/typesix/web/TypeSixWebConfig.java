package io.mipt.typesix.web;

import io.mipt.typesix.web.security.utils.YamlPropertySourceFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:web.properties")
@PropertySource(value = "classpath:clients.yaml", factory = YamlPropertySourceFactory.class)
public class TypeSixWebConfig {

}
