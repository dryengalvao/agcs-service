package br.com.agcs.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador responsável por fornecer o status da aplicação e do banco de
 * dados pelo endpoint health check.
 * 
 */

@Controller
@RequestMapping("/api")
public class HealthController {
	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
	
	@Autowired
	private Map<String, HealthIndicator> indicators;

	@GetMapping("/health")
	public ResponseEntity<Map<String, String>> statusHealth() {
		
		logger.info("Solicitação de status da aplicação - GET");
		
		Map<String, String> status = new HashMap<>();

		HealthIndicator applicationHelthIndicator = indicators.get("pingHealthContributor");
		HealthIndicator databaseHelthIndicator = indicators.get("diskSpaceHealthIndicator");
		
		status.put("applicationStatus", applicationHelthIndicator != null ? applicationHelthIndicator.health().getStatus().getCode() : "UNKNOWN");
		status.put("databaseStatus", databaseHelthIndicator != null ? databaseHelthIndicator.health().getStatus().getCode() : "UNKNOWN");
		
		logger.info("Status atual: Aplicação: {} , Banco de Dados: {}",status.get("applicationStatus"),status.get("databaseStatus"));
		
		return ResponseEntity.ok(status);
	}
}
