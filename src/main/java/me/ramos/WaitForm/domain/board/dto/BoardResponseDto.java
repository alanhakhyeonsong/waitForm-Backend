package me.ramos.WaitForm.domain.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ramos.WaitForm.domain.board.entity.Board;
import me.ramos.WaitForm.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    // Detail Info Response
    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Long memberId;
    private String writerNickname;

    public static BoardResponseDto of(Board board, Member member) {
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContent(), board.getCreatedDate(), member.getId(), member.getNickname());
    }

    public static BoardResponseDto of(Board board) {
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContent(), board.getCreatedDate(), board.getMember().getId(), board.getMember().getNickname());
    }

    @QueryProjection
    public BoardResponseDto(Long boardId, String title, String content, LocalDateTime createdDate, Long memberId, String writerNickname) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.memberId = memberId;
        this.writerNickname = writerNickname;
    }
}
