package me.ramos.WaitForm.domain.recommend.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ramos.WaitForm.domain.board.entity.Board;
import me.ramos.WaitForm.domain.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "recommends")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommend {

    @Id
    @Column(name = "recommend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Recommend(Member member, Board board) {
        this.member = member;
        this.board = board;
    }
}