package me.ramos.WaitForm.domain.board.repository;

import me.ramos.WaitForm.domain.board.entity.Board;
import me.ramos.WaitForm.domain.board.repository.querydsl.BoardCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

    @Query("select b from Board b join fetch b.member where b.id = :boardId")
    Optional<Board> findWithMemberById(@Param("boardId") Long boardId);
}
