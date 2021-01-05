package com.example.orbix_web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.orbix_web.application.JavaFxApplication;
import com.example.orbix_web.models.Item;
import com.example.orbix_web.repositories.CustomerRepository;
import com.example.orbix_web.repositories.ItemRepository;

import javafx.application.Application;


@SpringBootApplication()
@ComponentScan(basePackages={"com.example.orbix_web"})
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {CustomerRepository.class})
public class OrbixWebApplication {
	
	
	protected ConfigurableApplicationContext springContext;
	
	public static void main(String[] args) throws Throwable {
		SpringApplication.run(OrbixWebApplication.class, args);
		
		//Application.launch(JavaFxApplication.class,args);
		
		System.out.println((new Date()).toString());
	}
	
	
	
	
}
