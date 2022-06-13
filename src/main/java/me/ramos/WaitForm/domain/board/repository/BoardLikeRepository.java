package me.ramos.WaitForm.domain.board.repository;

import me.ramos.WaitForm.domain.board.entity.Board;
import me.ramos.WaitForm.domain.board.entity.BoardLike;
import me.ramos.WaitForm.domain.board.repository.querydsl.BoardLikeCustomRepository;
import me.ramos.WaitForm.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long>, BoardLikeCustomRepository {

    Optional<BoardLike> findByMemberAndBoard(Member member, Board board);
}
