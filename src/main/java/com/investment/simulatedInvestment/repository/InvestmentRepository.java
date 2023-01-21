package com.investment.simulatedInvestment.repository;

import com.investment.simulatedInvestment.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
}
