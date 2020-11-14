package com.example.orbix_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.orbix_web.application.JavaFxApplication;
import com.example.orbix_web.repositories.CustomerRepository;

import javafx.application.Application;


@SpringBootApplication()
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {CustomerRepository.class})
public class OrbixWebApplication {
	
	protected ConfigurableApplicationContext springContext;
	@Autowired
	public static void main(String[] args) throws Throwable {
		//SpringApplication.run(OrbixWebApplication.class, args);
		Application.launch(JavaFxApplication.class,args);
	}
}
