package br.com.agcs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO utilizado para criar novos tickets no sistema.
 * 
 * Este objeto carrega os dados recebidos da requisição HTTP para a camada de serviço,
 * garantindo a validação dos campos obrigatórios e a estrutura mínima necessária para o registro do ticket.
 * 
 * Validações aplicadas:
 * - title: obrigatório, mínimo de 3 caracteres.
 * - description: obrigatório, mínimo de 3 caracteres.
 * - category: obrigatório.
 * - sentiment: opcional.
 */

public record TicketDTOCreate(
		@NotBlank(message = "O campo Title é obrigatório") @Size(min = 3, message = "O campo Title deve conter 3 caracteres ou mais") String title,
	    @NotBlank(message = "O campo Description é obrigatório") @Size(min = 3, message = "O campo Description deve conter 3 caracteres ou mais")  String description,
	    @NotNull(message = "O campo Category é obrigatória") String category,
	    String sentiment) {

}
