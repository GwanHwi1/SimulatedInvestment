package com.investment.simulatedInvestment.entity;

import com.investment.simulatedInvestment.common.BaseTime;
import com.investment.simulatedInvestment.common.Read;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SendNote extends BaseTime {

    @Id
    @Column(name = "send_note_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Read read;

    private String title;
    private String content;

    @Builder
    public SendNote(Member member, Read read, String title, String content) {
        this.member = member;
        this.read = read;
        this.title = title;
        this.content = content;
    }
}
