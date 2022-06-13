package me.ramos.WaitForm.domain.recommend.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.member.entity.QMember;
import me.ramos.WaitForm.domain.recommend.domain.QRecommend;
import me.ramos.WaitForm.domain.recommend.dto.QRecommendResponse;
import me.ramos.WaitForm.domain.recommend.dto.RecommendResponse;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendCustomRepositoryImpl implements RecommendCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecommendResponse> findAllByMemberId(Long memberId) {
        QRecommend recommend = QRecommend.recommend;
        QMember member = QMember.member;

        List<RecommendResponse> results = queryFactory
                .select(new QRecommendResponse(
                        recommend.id,
                        recommend.board.id,
                        recommend.board.title,
                        recommend.member.id
                ))
                .from(recommend)
                .innerJoin(recommend.member, member)
                .on(recommend.member.id.eq(memberId))
                .fetch();

        return results;
    }
}
