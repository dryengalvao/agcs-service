package br.com.agcs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO utilizado para atualização de um ticket existente.
 * 
 * Este objeto é usado nas requisições PUT para validar e transferir dados que serão atualizados.
 * 
 * Validações aplicadas:
 * - title: obrigatório, com no mínimo 3 caracteres.
 * - description: obrigatório, com no mínimo 3 caracteres.
 * - category: obrigatório.
 * 
 * Observações:
 * - O campo sentiment não é atualizado diretamente nesse record.
 * - Os campos de ID e datas são gerenciados internamente na classe de serviço.
 */

public record TicketDTOUpdate(
		@NotBlank(message = "O campo Title é obrigatório") @Size(min = 3, message = "O campo Title deve conter 3 caracteres ou mais") String title,
	    @NotBlank(message = "O campo Description é obrigatório") @Size(min = 3, message = "O campo Description deve conter 3 caracteres ou mais")  String description,
	    @NotNull(message = "O campo Category é obrigatória") String category) {

}
