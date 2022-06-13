package me.ramos.WaitForm.domain.recommend.service;

import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.board.entity.Board;
import me.ramos.WaitForm.domain.board.exception.BoardNotFoundException;
import me.ramos.WaitForm.domain.board.repository.BoardRepository;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.exception.MemberNotFoundException;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import me.ramos.WaitForm.domain.recommend.domain.Recommend;
import me.ramos.WaitForm.domain.recommend.dto.ClusteredMember;
import me.ramos.WaitForm.domain.recommend.dto.RecommendRequest;
import me.ramos.WaitForm.domain.recommend.dto.RecommendResponse;
import me.ramos.WaitForm.domain.recommend.exception.RecommendNotFoundException;
import me.ramos.WaitForm.domain.recommend.repository.RecommendRepository;
import me.ramos.WaitForm.global.config.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {

    private final RecommendRepository recommendRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    // 저장
    @Transactional
    public void save(RecommendRequest request) {
        Long boardId = request.getBoardId();
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        for (ClusteredMember member : request.getMembers()) {
            Long memberId = member.getMemberId();
            Member findMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
            recommendRepository.save(new Recommend(findMember, board));
        }
    }

    // 멤버 id로 조회
    public List<RecommendResponse> getMyRecommends() {
        final Long memberId = SecurityUtil.getCurrentMemberId();
        List<RecommendResponse> list = recommendRepository.findAllByMemberId(memberId);

        if (list.size() == 0) {
            throw new RecommendNotFoundException();
        }

        return list;
    }

}
