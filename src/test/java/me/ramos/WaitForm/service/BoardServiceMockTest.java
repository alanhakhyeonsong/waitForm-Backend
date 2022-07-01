package me.ramos.WaitForm.service;

import me.ramos.WaitForm.domain.board.dto.BoardEnrollRequestDto;
import me.ramos.WaitForm.domain.board.dto.BoardResponseDto;
import me.ramos.WaitForm.domain.board.entity.Board;
import me.ramos.WaitForm.domain.board.repository.BoardRepository;
import me.ramos.WaitForm.domain.board.service.BoardService;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import me.ramos.WaitForm.global.config.util.SecurityUtil;
import me.ramos.WaitForm.support.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.ramos.WaitForm.support.helper.BoardTestHelper.givenBoard;
import static me.ramos.WaitForm.support.helper.MemberTestHelper.givenMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BoardServiceMockTest {

    @Mock
    BoardRepository boardRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    SecurityUtil securityUtil;

    @InjectMocks
    BoardService boardService;

    @WithMockCustomUser
    @Test
    public void 게시글_등록() throws Exception {
        //given
        Member member = givenMember();
        Board board = givenBoard(member);
        given(memberRepository.findById(securityUtil.getCurrentMemberId())).willReturn(Optional.of(member));
        given(boardRepository.save(any())).willReturn(board);

        //when
        BoardResponseDto result = boardService.upload(new BoardEnrollRequestDto(board.getTitle(), board.getContent()));

        //then
        assertThat(result.getTitle()).isEqualTo(board.getTitle());
        assertThat(result.getWriterNickname()).isEqualTo(member.getNickname());
    }
}
