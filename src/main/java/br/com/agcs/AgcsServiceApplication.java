package br.com.agcs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("br.com.agcs.entity")
@EnableJpaRepositories("br.com.agcs.repository")
@SpringBootApplication
public class AgcsServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(AgcsServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AgcsServiceApplication.class, args);

		logger.info("[INFO] Aplicação online!");
	}

}
