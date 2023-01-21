package com.investment.simulatedInvestment.entity;

import com.investment.simulatedInvestment.common.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Investment extends BaseTime {

    @Id
    @Column(name = "investment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "investment_company")
    private String company;

    private Long price;

    private int ea;

    @Builder
    public Investment(Member member, String company, Long price, int ea) {
        this.member = member;
        this.company = company;
        this.price = price;
        this.ea = ea;
    }
}
