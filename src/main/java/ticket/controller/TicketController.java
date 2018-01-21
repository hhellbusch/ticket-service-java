package ticket.controller;

import ticket.common.entity.Customer;
import ticket.service.TicketService;
import ticket.controller.entity.*;
import ticket.common.entity.SeatHold;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class TicketController {

	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);


	private final TicketService ticketService;

	public TicketController(final TicketService ticketService) 
	{
		logger.debug("-- Ticket Controller constructed --");
		this.ticketService = ticketService;
	}

	@PostMapping("/ticket/hold")
	public Response holdSeats(
		@RequestParam("email") String email
		, @RequestParam("count") int numSeats)
	{
		// TODO figure out how to send an appropiate HTTP response code
		// Might be helpful - 
		//  http://www.baeldung.com/spring-mvc-controller-custom-http-status-code
		//  https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
		if (numSeats > this.ticketService.numSeatsAvailable()) {
			ErrorResponse response  = new ErrorResponse();
			response.setError("Number of seats requests exceeds seats available.");
			return response;
		}
		SeatHold hold = this.ticketService.findAndHoldSeats(numSeats, email);
		
		CustomResponse response = new CustomResponse();
		response.setData(hold);
		return response;
	}



	@GetMapping("/ticket/availableSeatCount")
	public Response availableSeatCount()
	{
		CustomResponse response = new CustomResponse();
		response.setData(this.ticketService.numSeatsAvailable());
		return response;
	}



	//TODO figure out how to get variable from address
	// @PostMapping("/ticket/confirm/:id")
	// public Response confirmSeats()
	// {

	// }


}
