package br.com.agcs.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.agcs.dto.TicketDTOCreate;
import br.com.agcs.dto.TicketDTOResponse;
import br.com.agcs.dto.TicketDTOUpdate;
import br.com.agcs.entity.Ticket;
import br.com.agcs.repository.TicketRepository;
import br.com.agcs.service.TicketService;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository repository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private TicketDTOCreate ticketDTOCreate;
    private TicketDTOUpdate ticketDTOUpdate;

    @BeforeEach
    void setUp() {
        ticket = new Ticket(1L, "Teste Ticket", "Descrição para Ticket", "Hardware", "Não Informado", LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1));
        ticketDTOCreate = new TicketDTOCreate("Teste Criação Ticket", "Descrição para Ticket a ser Criado", "Hardware", null);
        ticketDTOUpdate = new TicketDTOUpdate("Teste Atualização Ticket", "Descrição para Ticket a ser Atualizado", "Software");
    }

    @Test
    void saveTicket_Success() {
        Ticket ticketCreated = new Ticket();
        ticketCreated.setId(1L);
        ticketCreated.setTitle(ticketDTOCreate.title());
        ticketCreated.setDescription(ticketDTOCreate.description());
        ticketCreated.setCategory(ticketDTOCreate.category());
        ticketCreated.setSentiment("Não Informado");
        ticketCreated.setCreatedAt(LocalDateTime.now());
        ticketCreated.setUpdatedAt(LocalDateTime.now());

        when(repository.save(any(Ticket.class))).thenReturn(ticketCreated);

        TicketDTOResponse response = ticketService.save(ticketDTOCreate);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Teste Criação Ticket", response.title());
        assertEquals("Não Informado", response.sentiment());
        verify(repository, times(1)).save(any(Ticket.class));
    }

    @Test
    void listAllTickets_Success() {
        when(repository.findAll()).thenReturn(List.of(ticket));

        List<TicketDTOResponse> tickets = ticketService.listAll();

        assertFalse(tickets.isEmpty());
        assertEquals(1, tickets.size());
        assertEquals("Teste Ticket", tickets.get(0).title());
        verify(repository, times(1)).findAll();
    }

    @Test
    void updateTicket_Success() {
        LocalDateTime valueBeforeUpdate = ticket.getUpdatedAt();

        when(repository.findById(1L)).thenReturn(Optional.of(ticket));
        
        when(repository.save(any(Ticket.class))).thenAnswer(ticketReturned -> {
            Ticket ticketToUpdate = ticketReturned.getArgument(0);
            ticketToUpdate.setUpdatedAt(LocalDateTime.now());
            return ticketToUpdate;
        });

        TicketDTOResponse response = ticketService.update(1L, ticketDTOUpdate);

        assertNotNull(response);
        assertEquals("Teste Atualização Ticket", response.title());
        assertEquals("Software", response.category());
        assertTrue(response.updatedAt().isAfter(valueBeforeUpdate));
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Ticket.class));
    }
}
