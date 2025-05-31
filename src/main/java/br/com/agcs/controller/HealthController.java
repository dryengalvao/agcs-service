package br.com.agcs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/health")
public class HealthController {

	@GetMapping
	public ResponseEntity<String> healthCheck() {
	    return ResponseEntity.ok("OK");
	}
}
