package me.ramos.WaitForm.support;

import me.ramos.WaitForm.domain.board.repository.querydsl.BoardCustomRepositoryImpl;
import me.ramos.WaitForm.domain.chat.repository.jdbc.JoinRoomJdbcRepositoryImpl;
import me.ramos.WaitForm.domain.chat.repository.jdbc.RoomMemberJdbcRepositoryImpl;
import me.ramos.WaitForm.domain.chat.repository.querydsl.JoinRoomQuerydslRepositoryImpl;
import me.ramos.WaitForm.domain.chat.repository.querydsl.MessageQuerydslRepositoryImpl;
import me.ramos.WaitForm.domain.recommend.repository.querydsl.RecommendCustomRepositoryImpl;
import me.ramos.WaitForm.global.config.QuerydslConfig;
import org.springframework.context.annotation.Import;

@Import({
        QuerydslConfig.class,
        BoardCustomRepositoryImpl.class,
        RecommendCustomRepositoryImpl.class,
        JoinRoomJdbcRepositoryImpl.class,
        JoinRoomQuerydslRepositoryImpl.class,
        RoomMemberJdbcRepositoryImpl.class,
        MessageQuerydslRepositoryImpl.class
})
public abstract class QuerydslRepositoryTest extends JpaRepositoryTest {
}
