package me.ramos.WaitForm.domain.chat.repository.jdbc;

import me.ramos.WaitForm.domain.chat.entity.Room;
import me.ramos.WaitForm.domain.member.entity.Member;

import java.util.List;

public interface RoomMemberJdbcRepository {

    void saveAllBatch(Room room, List<Member> members);
}
