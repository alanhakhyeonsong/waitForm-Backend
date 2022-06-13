package me.ramos.WaitForm.domain.board.service;

import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.board.dto.BoardEnrollRequestDto;
import me.ramos.WaitForm.domain.board.dto.BoardLikeResponseDto;
import me.ramos.WaitForm.domain.board.dto.BoardResponseDto;
import me.ramos.WaitForm.domain.board.entity.Board;
import me.ramos.WaitForm.domain.board.entity.BoardLike;
import me.ramos.WaitForm.domain.board.exception.BoardLikeNotFoundException;
import me.ramos.WaitForm.domain.board.exception.BoardNotFoundException;
import me.ramos.WaitForm.domain.board.repository.BoardLikeRepository;
import me.ramos.WaitForm.domain.board.repository.BoardRepository;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.exception.MemberNotFoundException;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import me.ramos.WaitForm.global.config.firebase.FirebaseService;
import me.ramos.WaitForm.global.config.util.SecurityUtil;
import me.ramos.WaitForm.global.error.exception.EntityAlreadyExistException;
import me.ramos.WaitForm.global.error.exception.NoAuthorityException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static me.ramos.WaitForm.global.error.ErrorCode.BOARD_LIKE_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final FirebaseService firebaseService;

    // 등록
    @Transactional
    public BoardResponseDto upload(BoardEnrollRequestDto request) throws Exception {
        final Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .member(member)
                .build();

        Board save = boardRepository.save(board);
        firebaseService.insertBoard(save);

        return BoardResponseDto.of(board, member);
    }

    // 삭제
    @Transactional
    public void delete(Long boardId) {
        final Long loginMemberId = SecurityUtil.getCurrentMemberId();
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        if (!board.getMember().getId().equals(loginMemberId)) {
            throw new NoAuthorityException();
        }
        boardRepository.deleteById(boardId);
    }

    // 본인이 쓴 글 목록 조회
    public List<BoardResponseDto> getMyBoardList() {
        final Long memberId = SecurityUtil.getCurrentMemberId();

        List<BoardResponseDto> list = boardRepository.findAllByMemberId(memberId);
        if (list.size() == 0) {
            throw new BoardNotFoundException();
        }
        return list;
    }

    // 단일 조회(상세)
    public BoardResponseDto findDetailInfo(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        return BoardResponseDto.of(board);
    }

    // 좋아요
    @Transactional
    public BoardLikeResponseDto likeBoard(Long boardId) {
        final Board board = boardRepository.findWithMemberById(boardId).orElseThrow(BoardNotFoundException::new);
        final Long memberId = SecurityUtil.getCurrentMemberId();
        Member loginMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        if (boardLikeRepository.findByMemberAndBoard(loginMember, board).isPresent()) {
            throw new EntityAlreadyExistException(BOARD_LIKE_ALREADY_EXIST);
        }

        BoardLike save = boardLikeRepository.save(new BoardLike(loginMember, board));

        return BoardLikeResponseDto.of(save, board, loginMember);
    }

    // 게시글에 좋아요 눌린 목록 조회
    public List<BoardLikeResponseDto> findLikesByBoard(Long boardId) {
        List<BoardLikeResponseDto> list = boardLikeRepository.findAllByBoardId(boardId);
        if (list.size() == 0) {
            throw new BoardLikeNotFoundException();
        }
        return list;
    }

    // 자신이 누른 좋아요 목록 조회
    public List<BoardLikeResponseDto> getMyLikes() {
        final Long memberId = SecurityUtil.getCurrentMemberId();
        List<BoardLikeResponseDto> list = boardLikeRepository.findAllByMemberId(memberId);

        if (list.size() == 0) {
            throw new BoardLikeNotFoundException();
        }
        return list;
    }
}