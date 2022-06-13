package me.ramos.WaitForm.domain.board.repository.querydsl;

import me.ramos.WaitForm.domain.board.dto.BoardLikeResponseDto;

import java.util.List;

public interface BoardLikeCustomRepository {
    List<BoardLikeResponseDto> findAllByBoardId(Long boardId);
    List<BoardLikeResponseDto> findAllByMemberId(Long memberId);

}
