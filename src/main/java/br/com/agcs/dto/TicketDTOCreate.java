package br.com.agcs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketDTOCreate(
		@NotBlank(message = "O campo Title é obrigatório") @Size(min = 3, message = "O campo Title deve conter 3 caracteres ou mais") String title,
	    @NotBlank(message = "O campo Description é obrigatório") @Size(min = 3, message = "O campo Description deve conter 3 caracteres ou mais")  String description,
	    @NotNull(message = "O campo Category é obrigatória") String category,
	    String sentiment) {

}
