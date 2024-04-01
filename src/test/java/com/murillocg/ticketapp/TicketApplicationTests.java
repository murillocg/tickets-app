package com.murillocg.ticketapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.DispatcherServlet;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class TicketApplicationTests {

	@Container
	private static final PostgreSQLContainer<?> postgresContainer =
			new PostgreSQLContainer<>("postgres:latest")
					.withDatabaseName("ticketsdb")
					.withUsername("postgres")
					.withPassword("password");

	@Autowired
	private DispatcherServlet dispatcherServlet;

	@Test
	public void shouldLoadContextSuccessfully() {
		Assertions.assertNotNull(dispatcherServlet);
	}

}
