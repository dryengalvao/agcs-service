package br.com.agcs.exception;

import org.springframework.http.HttpStatus;

public class TicketNotFoundException extends ApplicationException{
	
	private static final long serialVersionUID = 1L;

	public TicketNotFoundException(Long id) {
        super("Ticket com ID " + id + " não encontrado.", HttpStatus.NOT_FOUND);
    }
	
}
