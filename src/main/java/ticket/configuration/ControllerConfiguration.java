package ticket.configuration;

import ticket.controller.TicketController;
import ticket.service.TicketService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ControllerConfiguration {
	
	@Bean
	public TicketController ticketController(final TicketService ticketService){
		return new TicketController(ticketService);
	}

}