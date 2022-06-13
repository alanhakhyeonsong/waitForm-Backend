package me.ramos.WaitForm.domain.recommend.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendResponse {

    private Long recommendId;
    private Long boardId;
    private String boardTitle;
    private Long memberId;

    @QueryProjection
    public RecommendResponse(Long recommendId, Long boardId, String boardTitle, Long memberId) {
        this.recommendId = recommendId;
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.memberId = memberId;
    }
}
