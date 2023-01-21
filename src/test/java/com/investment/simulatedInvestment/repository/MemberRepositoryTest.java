package com.investment.simulatedInvestment.repository;

import com.investment.simulatedInvestment.common.Role;
import com.investment.simulatedInvestment.entity.Member;
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
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 저장 성공")
    public void saveMember() {

        assertThatCode(() -> {
            Member member = new Member("jo101044", "pjy0381", "1234", Role.USER);
            Member save = memberRepository.save(member);

            assertEquals(save.getId(), member.getId());
            assertEquals(save.getUsername(), member.getUsername());
            assertEquals(save.getCreatedDate(), member.getCreatedDate());
        }).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("회원 저장 실패")
    public void saveMember_fail() {

        assertThatCode(() -> {
            Member member = new Member("jo101044", "pjy0381", "1234", Role.USER);
            Member member2 = new Member("jo101044", "pjy0381", "1234", Role.USER);
            memberRepository.save(member);
            memberRepository.save(member2);

        }).isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    @DisplayName("회원 이름 조회")
    public void memberFindName() {

        assertThatCode(() -> {
            Member member = new Member("jo101044", "pjy0381", "1234", Role.USER);
            memberRepository.save(member);

            Member result = memberRepository.findByUsername(member.getUsername())
                    .orElseThrow(NoSuchElementException::new);

            assertEquals(result.getId(), member.getId());
            assertEquals(result.getUsername(), member.getUsername());
            assertEquals(result.getCreatedDate(), member.getCreatedDate());
        }).doesNotThrowAnyException();

    }
}