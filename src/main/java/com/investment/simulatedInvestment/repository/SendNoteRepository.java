package com.investment.simulatedInvestment.repository;

import com.investment.simulatedInvestment.entity.SendNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendNoteRepository extends JpaRepository<SendNote, Long> {
}