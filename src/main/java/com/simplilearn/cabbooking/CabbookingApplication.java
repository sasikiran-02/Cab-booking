package com.simplilearn.cabbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.simplilearn.cabbooking.Repository","com.simplilearn.cabbooking.Service","com.simplilearn.cabbooking.model","com.simplilearn.cabbooking.RestControllers"})
public class CabbookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabbookingApplication.class, args);
	}

}
