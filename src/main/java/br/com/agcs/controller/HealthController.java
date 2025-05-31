package br.com.agcs.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class HealthController {
	
	@Autowired
	private Map<String, HealthIndicator> indicators;

	@GetMapping("/health")
	public ResponseEntity<Map<String, Object>> statusHelth() {
	    Map<String, Object> result = new HashMap<>();
	    indicators.forEach((name, indicator) -> {
	        result.put(name, indicator.health());
	    });
	    return ResponseEntity.ok(result);
	}
}
