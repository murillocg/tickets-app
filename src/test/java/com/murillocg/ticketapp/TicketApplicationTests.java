package com.murillocg.ticketapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootTest
public class TicketApplicationTests {

	@Autowired
	private DispatcherServlet dispatcherServlet;

	@Test
	public void shouldLoadContextSuccessfully() {
		Assertions.assertNotNull(dispatcherServlet);
	}

}
