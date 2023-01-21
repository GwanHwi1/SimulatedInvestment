package com.investment.simulatedInvestment.repository;

import com.investment.simulatedInvestment.common.Role;
import com.investment.simulatedInvestment.entity.Investment;
import com.investment.simulatedInvestment.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class InvestmentRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    @BeforeEach
    void createMeber() {
        Member member = new Member("jo101044", "pjy0381", "1234", Role.USER);
        memberRepository.save(member);
    }

    @Test
    @DisplayName("모의 투자 성공")
    public void saveInvestment() {
        assertThatCode(() -> {
            Member findMember = memberRepository.findById(1L).orElseThrow(NoSuchElementException::new);

            Investment investment = new Investment(findMember, "삼성전자", 80000L, 10);
            Investment result = investmentRepository.save(investment);

            assertEquals(result.getCompany(), "삼성전자");
            assertEquals(result.getPrice(), 80000L);
            assertEquals(result.getEa(), 10);
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("모의 투자 성공")
    public void saveInvestment_fail() {
        assertThatCode(() -> {
            Member member = memberRepository.findById(0L).orElse(null);

            investmentRepository.save(new Investment(member, "삼성전자", 80000L, 10));

        }).isInstanceOf(DataIntegrityViolationException.class);
    }

}