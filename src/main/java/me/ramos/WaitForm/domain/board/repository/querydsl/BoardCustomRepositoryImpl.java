package me.ramos.WaitForm.domain.board.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.board.dto.BoardResponseDto;
import me.ramos.WaitForm.domain.board.dto.QBoardResponseDto;
import me.ramos.WaitForm.domain.board.entity.QBoard;
import me.ramos.WaitForm.domain.member.entity.QMember;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardResponseDto> findAllByMemberId(Long memberId) {
        QBoard board = QBoard.board;
        QMember member = QMember.member;

        List<BoardResponseDto> results = queryFactory
                .select(new QBoardResponseDto(
                        board.id,
                        board.title,
                        board.content,
                        board.createdDate,
                        board.member.id,
                        member.nickname
                ))
                .from(board)
                .innerJoin(board.member, member)
                .on(member.id.eq(memberId))
                .orderBy(board.createdDate.desc())
                .fetch();

        return results;
    }
}
