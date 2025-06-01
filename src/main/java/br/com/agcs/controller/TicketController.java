package br.com.agcs.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agcs.dto.TicketDTOCreate;
import br.com.agcs.dto.TicketDTOResponse;
import br.com.agcs.dto.TicketDTOUpdate;
import br.com.agcs.service.TicketService;
import jakarta.validation.Valid;

/**
 * Controlador REST responsável por expor os endpoints relacionados aos Tickets.
 * 
 * Endpoints disponíveis:
 * - POST /api/tickets     → Criação de um novo ticket
 * - GET  /api/tickets     → Listagem de todos os tickets
 * - PUT  /api/tickets/{id} → Atualização de um ticket existente
 * 
 * Observação:
 * - As validações de obrigatoriedade e tamanho dos campos do Ticket são tratados nos DTOs específicos de cada endpoint
 */

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketService ticketService;

	@PostMapping
    public ResponseEntity<TicketDTOResponse> create(@Valid @RequestBody TicketDTOCreate newTicket) {
		
        logger.info("Solicitação para criação de um novo ticket recebida - POST");
        logger.debug("Registro: {}",newTicket);
        TicketDTOResponse ticketCreated = ticketService.save(newTicket);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCreated);
        
    }

    @GetMapping
    public List<TicketDTOResponse> list() {
    	
        logger.info("Solicitação para listagem de todos os tickets recebida - GET");
        return ticketService.listAll();
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTOResponse> update(@PathVariable Long id, @Valid @RequestBody TicketDTOUpdate updateTicket) {
    	
        logger.info("Solicitação para atualização do ticket ID: {} recebida - PUT", id);
        logger.debug("Registro: {}",updateTicket);
        TicketDTOResponse ticketUpdated = ticketService.update(id, updateTicket);
        return ResponseEntity.ok(ticketUpdated);
        
    }

}