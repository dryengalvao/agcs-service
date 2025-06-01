package br.com.agcs.dto;

import java.time.LocalDateTime;

/**
 * DTO utilizado para retornar informações completas de um ticket em resposta a uma requisição GET.
 * 
 * Essa estrutura é usada nas respostas de API para representar um ticket com todos os campos,
 * incluindo os campos de auditoria (createdAt e updatedAt).
 * 
 * Observação:
 * - Esse objeto deverá ser utilizado apenas em endpoint de consulta (GET), 
 * os endpoint de entrada (POST / PUT) possuem DTOs próprios.
 */

public record TicketDTOResponse(Long id, String title, String description, String category,
		String sentiment, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
