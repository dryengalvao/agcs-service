package br.com.agcs.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agcs.dto.TicketDTOCreate;
import br.com.agcs.dto.TicketDTOResponse;
import br.com.agcs.dto.TicketDTOUpdate;
import br.com.agcs.entity.Ticket;
import br.com.agcs.exception.TicketNotFoundException;
import br.com.agcs.repository.TicketRepository;
import jakarta.transaction.Transactional;

/**
 * Classe de serviço responsável pela lógica de negócios da entidade Ticket.
 * 
 * Responsabilidades:
 * - Gerenciar operações de criação, listagem e atualização de tickets.
 * - Converter entidades em DTOs e vice-versa.
 * - Tratar erros como ticket não encontrado.
 * - Anotação @Transactional foi adicionada para garantir atomicidade nas operações de escrita.
 * - Pelas definições dos requisitos o campo "sentiment" não é obrigatório durante a criação do ticket
 *  desse modo foi definido um valor padrão "Não Informado" se nulo nenhum valor for informado.
 */

@Service
public class TicketService {

	private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

	@Autowired
	private TicketRepository repository;

	public List<TicketDTOResponse> listAll() {

		logger.info("Listando todos os tickets");
        List<Ticket> ticketList = repository.findAll();
        List<TicketDTOResponse> ticketResponseList = ticketList.stream()
            .map(ticket -> new TicketDTOResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getCategory(),
                ticket.getSentiment(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
            )).collect(Collectors.toList());

        logger.debug("Foram encontrados {} tickets", ticketResponseList.size());

        return ticketResponseList;

	}


	@Transactional
    public TicketDTOResponse save(TicketDTOCreate newTicket) {

        logger.info("Salvando novo ticket: {}", newTicket);

        Ticket ticketToCreate = new Ticket();
        ticketToCreate.setTitle(newTicket.title());
        ticketToCreate.setDescription(newTicket.description());
        ticketToCreate.setCategory(newTicket.category());
        ticketToCreate.setSentiment(newTicket.sentiment() != null ? newTicket.sentiment() : "Não Informado"); //caso nenhum valor seja passado o valor padrão é definido
        ticketToCreate.setCreatedAt(LocalDateTime.now());
        ticketToCreate.setUpdatedAt(LocalDateTime.now());

        Ticket ticketCreated = repository.save(ticketToCreate);

        logger.debug("Ticket criado com sucesso, ID: {}", ticketCreated.getId());

        return new TicketDTOResponse(
            ticketCreated.getId(),
            ticketCreated.getTitle(),
            ticketCreated.getDescription(),
            ticketCreated.getCategory(),
            ticketCreated.getSentiment(),
            ticketCreated.getCreatedAt(),
            ticketCreated.getUpdatedAt()
        );
    }


	@Transactional
    public TicketDTOResponse update(Long id, TicketDTOUpdate updateTicket) {
        logger.info("Atualizando ticket ID: {}", id);

        Ticket ticket = repository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));

        ticket.setTitle(updateTicket.title());
        ticket.setDescription(updateTicket.description());
        ticket.setCategory(updateTicket.category());
        ticket.setUpdatedAt(LocalDateTime.now());

        Ticket ticketUpdated = repository.save(ticket);
        logger.debug("Ticket atualizado com sucesso, ID: {}", ticketUpdated.getId());

        return new TicketDTOResponse(
            ticketUpdated.getId(),
            ticketUpdated.getTitle(),
            ticketUpdated.getDescription(),
            ticketUpdated.getCategory(),
            ticketUpdated.getSentiment(),
            ticketUpdated.getCreatedAt(),
            ticketUpdated.getUpdatedAt()
        );
    }
}
