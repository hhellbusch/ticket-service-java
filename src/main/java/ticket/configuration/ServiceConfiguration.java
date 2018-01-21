package ticket.configuration;

import ticket.common.entity.Venue;
import ticket.collection.CustomerCollection;
import ticket.service.TicketService;
import ticket.service.TicketServiceMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

// Configures the application to use a particular implementation of the ticket service
@Configuration
public class ServiceConfiguration
{
	@Bean
	public TicketService ticketService(
		final CustomerCollection customerCollection
		, final Environment environment
	) {
		int seatHoldDuration = environment.getProperty("seat.hold.expiration.seconds", Integer.class);
		
		int venueRows = environment.getProperty("venue.rows", Integer.class);
		int venueCols = environment.getProperty("venue.cols", Integer.class);
		Venue venue = new Venue(venueRows, venueCols);
		
		return new TicketServiceMemory(
			customerCollection
			, venue
			, seatHoldDuration
		);
	}
}
