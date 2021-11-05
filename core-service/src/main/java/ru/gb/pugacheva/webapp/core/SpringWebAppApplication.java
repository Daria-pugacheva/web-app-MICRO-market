package ru.gb.pugacheva.webapp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySource("secret.properties")
public class SpringWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebAppApplication.class, args);
	}

}
