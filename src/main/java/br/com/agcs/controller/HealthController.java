package br.com.agcs.controller;

import java.util.HashMap;
import java.util.Map;

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

	@Autowired
	private Map<String, HealthIndicator> indicators;

	@GetMapping("/health")
	public ResponseEntity<Map<String, String>> statusHealth() {
		Map<String, String> result = new HashMap<>();

		HealthIndicator applicationStatus = indicators.get("pingHealthContributor");
		HealthIndicator databaseStatus = indicators.get("diskSpaceHealthIndicator");

		result.put("applicationStatus",
				applicationStatus != null ? applicationStatus.health().getStatus().getCode() : "UNKNOWN");
		result.put("databaseStatus",
				databaseStatus != null ? databaseStatus.health().getStatus().getCode() : "UNKNOWN");

		return ResponseEntity.ok(result);
	}
}
