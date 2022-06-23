package me.ramos.WaitForm.repository;

import me.ramos.WaitForm.config.TestQuerydslConfig;
import me.ramos.WaitForm.domain.board.dto.BoardResponseDto;
import me.ramos.WaitForm.domain.board.entity.Board;
import me.ramos.WaitForm.domain.board.repository.BoardRepository;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static me.ramos.WaitForm.support.helper.BoardTestHelper.givenBoard;
import static me.ramos.WaitForm.support.helper.MemberTestHelper.givenMember;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestQuerydslConfig.class)
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 게시글_등록_테스트() throws Exception {
        //when
        Board savedBoard = boardRepository.save(givenBoard());

        //then
        assertThat(boardRepository.findById(savedBoard.getId())).isNotEmpty();
    }

    @Test
    public void 회원Id_기준_게시글_조회() throws Exception {
        //given
        Member savedMember = memberRepository.save(givenMember());
        Board savedBoard = boardRepository.save(givenBoard(savedMember));

        //when
        List<BoardResponseDto> result = boardRepository.findAllByMemberId(savedMember.getId());

        //then
        assertThat(result).isNotEmpty();
        assertThat(savedBoard.getMember()).isEqualTo(savedMember);
    }
}
