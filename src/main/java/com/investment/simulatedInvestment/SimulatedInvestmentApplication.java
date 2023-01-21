package com.investment.simulatedInvestment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SimulatedInvestmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulatedInvestmentApplication.class, args);
	}

}
