package br.com.agcs.integration;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.agcs.dto.TicketDTOCreate;

/**
 * Classe de teste de integração para o controlador de tickets
 * (TicketController). Utiliza MockMvc para simular requisições HTTP e validar o
 * comportamento do endpoint de criação de tickets.
 */

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerIntegrationTest {

	@Autowired
	private MockMvc mockTest;

	@Autowired
	private ObjectMapper objectMapper;

	/*
	 * Método responsável por validar a requisição POST. 
	 * Etapas:
	 * - Criar um novo objeto para ser persistido;
	 * - Realiza a requisição POST para o endpoint responsável;
	 * - Valida o retorno recebido.
	 */
	@Test
	void createTicket_ReturnsCreatedTicket() throws Exception {
		String titlePayload = "Título de Integração";
		String descriptionPayload = "Descrição de integração";
		String categoryPayload = "Software";
		String sentimentPayload = "Não Informado";
		
		TicketDTOCreate ticketDTO = new TicketDTOCreate( titlePayload, descriptionPayload, categoryPayload,null);

		mockTest.perform(post("/api/tickets").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ticketDTO))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.title", is(titlePayload)))
				.andExpect(jsonPath("$.description", is(descriptionPayload)))
				.andExpect(jsonPath("$.category", is(categoryPayload)))
				.andExpect(jsonPath("$.sentiment", is(sentimentPayload)));
	}
	
	@Test
	void createTicket_WithBlankFields_ReturnsValidationErrors() throws Exception {
		TicketDTOCreate ticketDTO = new TicketDTOCreate("", "", null, null);

		mockTest.perform(
				post("/api/tickets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ticketDTO)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errors").exists())
			.andExpect(jsonPath("$.errors.title").exists())
			.andExpect(jsonPath("$.errors.description").exists())
			.andExpect(jsonPath("$.errors.category").exists());
	}
}
