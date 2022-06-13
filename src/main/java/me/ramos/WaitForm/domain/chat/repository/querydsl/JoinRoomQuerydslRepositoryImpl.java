package me.ramos.WaitForm.domain.chat.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.chat.dto.JoinRoomDto;
import me.ramos.WaitForm.domain.chat.dto.MemberInfoDto;
import me.ramos.WaitForm.domain.chat.dto.MessageDto;
import me.ramos.WaitForm.domain.chat.dto.QJoinRoomDto;
import me.ramos.WaitForm.domain.chat.entity.Message;
import me.ramos.WaitForm.domain.chat.entity.RoomMember;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static me.ramos.WaitForm.domain.chat.entity.QJoinRoom.joinRoom;
import static me.ramos.WaitForm.domain.chat.entity.QMessage.message;
import static me.ramos.WaitForm.domain.chat.entity.QRoom.room;
import static me.ramos.WaitForm.domain.chat.entity.QRoomMember.roomMember;
import static me.ramos.WaitForm.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class JoinRoomQuerydslRepositoryImpl implements JoinRoomQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<JoinRoomDto> findAllJoinRoomDtoByMemberId(Long memberId) {
        List<JoinRoomDto> results = queryFactory
                .select(new QJoinRoomDto(
                        joinRoom.room.id,
                        joinRoom.room.member
                ))
                .from(joinRoom)
                .innerJoin(joinRoom.room, room)
                .innerJoin(joinRoom.message, message)
                .innerJoin(joinRoom.room.member, member)
                .on(joinRoom.member.id.eq(memberId))
                .fetch();

        final List<Long> roomIds = results.stream()
                .map(JoinRoomDto::getRoomId)
                .collect(Collectors.toList());

        final List<Message> messages = queryFactory
                .select(joinRoom.message)
                .from(joinRoom)
                .innerJoin(joinRoom.message, message)
                .where(joinRoom.member.id.eq(memberId))
                .fetch();

        final List<Long> messageIds = messages.stream()
                .map(Message::getId)
                .collect(Collectors.toList());

        final Map<Long, Message> messageMap = queryFactory
                .from(message)
                .where(message.id.in(messageIds))
                .transform(groupBy(message.room.id).as(message));

        final List<RoomMember> roomMembers = queryFactory
                .selectFrom(roomMember)
                .where(roomMember.room.id.in(roomIds))
                .join(roomMember.member, member).fetchJoin()
                .fetch();

        final Map<Long, List<RoomMember>> roomMemberMap = roomMembers.stream()
                .collect(Collectors.groupingBy(r -> r.getRoom().getId()));

        results.forEach(j -> {
            j.setMembers(
                    roomMemberMap.get(j.getRoomId()).stream()
                            .map(r -> new MemberInfoDto(r.getMember()))
                            .collect(Collectors.toList())
            );
            j.setLastMessage(new MessageDto());
        });

        return results;
    }
}
