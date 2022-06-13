package me.ramos.WaitForm.domain.board.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.board.dto.BoardLikeResponseDto;
import me.ramos.WaitForm.domain.board.dto.QBoardLikeResponseDto;
import me.ramos.WaitForm.domain.board.entity.QBoard;
import me.ramos.WaitForm.domain.board.entity.QBoardLike;
import me.ramos.WaitForm.domain.member.entity.QMember;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardLikeCustomRepositoryImpl implements BoardLikeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardLikeResponseDto> findAllByBoardId(Long boardId) {
        QBoardLike boardLike = QBoardLike.boardLike;
        QBoard board = QBoard.board;

        List<BoardLikeResponseDto> results = queryFactory
                .select(new QBoardLikeResponseDto(
                        boardLike.id,
                        boardLike.board.id,
                        boardLike.board.title,
                        boardLike.member.nickname
                ))
                .from(boardLike)
                .innerJoin(boardLike.board, board)
                .on(boardLike.board.id.eq(boardId))
                .fetch();

        return results;
    }

    @Override
    public List<BoardLikeResponseDto> findAllByMemberId(Long memberId) {
        QBoardLike boardLike = QBoardLike.boardLike;
        QMember member = QMember.member;

        List<BoardLikeResponseDto> results = queryFactory
                .select(new QBoardLikeResponseDto(
                        boardLike.id,
                        boardLike.board.id,
                        boardLike.board.title,
                        boardLike.member.nickname
                ))
                .from(boardLike)
                .innerJoin(boardLike.member, member)
                .on(boardLike.member.id.eq(memberId))
                .fetch();

        return results;
    }
}
