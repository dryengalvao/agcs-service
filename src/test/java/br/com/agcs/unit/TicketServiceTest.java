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

/**
 * Classe de teste unitário para o serviço de tickets (TicketService).
 * Utiliza Mockito para simular o comportamento do repositório e testar as funcionalidades
 * de criação, listagem e atualização de tickets de forma isolada sem a necessidade de um banco de dados.
 */

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository repository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private TicketDTOCreate ticketDTOCreate;
    private TicketDTOUpdate ticketDTOUpdate;

    // Setup inicial para definição dos objetos utilizados na carta de teste
    @BeforeEach
    void setUp() {
        ticket = new Ticket(1L, "Teste Ticket", "Descrição para Ticket", "Hardware", "Não Informado", LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1));
        ticketDTOCreate = new TicketDTOCreate("Teste Criação Ticket", "Descrição para Ticket a ser Criado", "Hardware", null);
        ticketDTOUpdate = new TicketDTOUpdate("Teste Atualização Ticket", "Descrição para Ticket a ser Atualizado", "Software");
    }

    /*
     *  Caso de teste para a rotina de criação de um novo ticket
     *  Etapas:
     *  - Salva o ticket no repositório.
     *  - Retorna o ticket criado com os dados.
     */
    
    @Test
    void saveTicket_Success() {
        Ticket ticketCreatedDefinition = new Ticket();
        ticketCreatedDefinition.setId(1L);
        ticketCreatedDefinition.setTitle(ticketDTOCreate.title());
        ticketCreatedDefinition.setDescription(ticketDTOCreate.description());
        ticketCreatedDefinition.setCategory(ticketDTOCreate.category());
        ticketCreatedDefinition.setSentiment("Não Informado");
        ticketCreatedDefinition.setCreatedAt(LocalDateTime.now());
        ticketCreatedDefinition.setUpdatedAt(LocalDateTime.now());

        when(repository.save(any(Ticket.class))).thenReturn(ticketCreatedDefinition);

        TicketDTOResponse response = ticketService.save(ticketDTOCreate);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Teste Criação Ticket", response.title());
        assertEquals("Não Informado", response.sentiment());
        verify(repository, times(1)).save(any(Ticket.class));
    }

    // Caso de teste para a rotina de listagem dos tickets criados
    @Test
    void listAllTickets_Success() {
        when(repository.findAll()).thenReturn(List.of(ticket));

        List<TicketDTOResponse> tickets = ticketService.listAll();

        assertFalse(tickets.isEmpty());
        assertEquals(1, tickets.size());
        verify(repository, times(1)).findAll();
    }

    /*
     * Caso de teste para a rotina de atualização de um ticket existente
     * Etapas:
     * - Obtem da data de atualização de um registro existente;
     * - Recupera um registro ticket existente com com base no ID;
     * - Atualiza os dados do ticket;
     * - Valida se os dados foram atualizados corretamente;
     */
    @Test
    void updateTicket_Success() {
        LocalDateTime dateBeforeUpdate = ticket.getUpdatedAt();

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
        assertTrue(response.updatedAt().isAfter(dateBeforeUpdate));
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Ticket.class));
    }
}
