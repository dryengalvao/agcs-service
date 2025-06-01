package br.com.agcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.agcs.entity.Ticket;

/**
 * Interface responsável por realizar a persistência da entidade Ticket.
 * A extensão da Interface JpaRepository garante que as operações padrões de CRUD sejam realizadas 
 * sem a necessidade de implementação manual.
 * 
 */

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
