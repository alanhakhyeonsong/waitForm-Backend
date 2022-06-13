package me.ramos.WaitForm.domain.recommend.repository.querydsl;

import me.ramos.WaitForm.domain.recommend.dto.RecommendResponse;

import java.util.List;

public interface RecommendCustomRepository {

    List<RecommendResponse> findAllByMemberId(Long memberId);
}
