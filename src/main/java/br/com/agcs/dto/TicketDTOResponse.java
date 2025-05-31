package br.com.agcs.dto;

import java.time.LocalDateTime;

public record TicketDTOResponse(Long id, String title, String description, String category,
		String sentiment, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
