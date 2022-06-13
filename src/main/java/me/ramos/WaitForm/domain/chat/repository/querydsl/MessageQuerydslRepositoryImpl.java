package me.ramos.WaitForm.domain.chat.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.chat.dto.MessageDto;
import me.ramos.WaitForm.domain.chat.entity.JoinRoom;
import me.ramos.WaitForm.domain.chat.entity.Message;
import me.ramos.WaitForm.domain.chat.exception.JoinRoomNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static me.ramos.WaitForm.domain.chat.entity.QJoinRoom.joinRoom;
import static me.ramos.WaitForm.domain.chat.entity.QMessage.message;
import static me.ramos.WaitForm.domain.chat.entity.QRoom.room;
import static me.ramos.WaitForm.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class MessageQuerydslRepositoryImpl implements MessageQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MessageDto> findAllMessageDtoByMemberIdAndRoomId(Long memberId, Long roomId) {

        final JoinRoom findJoinRoom = queryFactory
                .selectFrom(joinRoom)
                .where(joinRoom.member.id.eq(memberId).and(joinRoom.room.id.eq(roomId)))
                .innerJoin(joinRoom.room, room).fetchJoin()
                .innerJoin(joinRoom.member, member).fetchJoin()
                .fetchOne();

        if (findJoinRoom == null) {
            throw new JoinRoomNotFoundException();
        }

        final List<Message> messages = queryFactory
                .selectFrom(message)
                .where(message.room.id.eq(findJoinRoom.getRoom().getId()))
                .orderBy(message.id.desc())
                .fetch();

        final List<Long> messageIds = messages.stream()
                .map(Message::getId)
                .collect(Collectors.toList());

        final Map<Long, Message> messageMap = queryFactory
                .from(message)
                .where(message.id.in(messageIds))
                .transform(groupBy(message.id).as(message));

        final List<MessageDto> messageDtos = messages.stream()
                .map(m -> new MessageDto(messageMap.get(m.getId())))
                .collect(Collectors.toList());

        return messageDtos;
    }
}
