package com.murillocg.ticketapp;

import com.murillocg.ticketapp.entity.User;
import com.murillocg.ticketapp.enums.UserRole;
import com.murillocg.ticketapp.repository.UserRepository;
import com.murillocg.ticketapp.service.UserDetailsSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketApplication {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsSecurityService.class);

	public static void main(String[] args) {
		SpringApplication.run(TicketApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return args -> {

			var admin = new User();
			admin.setFirstName("Murillo");
			admin.setLastName("Goulart");
			admin.setLogin("murillocg");
			admin.setRole(UserRole.ADMIN);
			admin.setPassword("$2a$12$qYOKQfa0bcpMVVSnKv.MKObLvsfiAiZ8/1hBdbZbk2QwAFl2SGGL2");
			userRepository.saveAndFlush(admin);
			logger.info("Admin {} created successfully", admin.getLogin());

			var user = new User();
			user.setFirstName("José");
			user.setLastName("da Silva");
			user.setLogin("josessilva");
			user.setRole(UserRole.USER);
			user.setPassword("$2a$12$OD1HfDHAU79yDqEIXCJHB.yaMSV.TsPL1EZO.e9Ax55NIAhVYo9sG");
			userRepository.saveAndFlush(user);
			logger.info("User {} created successfully", user.getLogin());
		};
	}
}
