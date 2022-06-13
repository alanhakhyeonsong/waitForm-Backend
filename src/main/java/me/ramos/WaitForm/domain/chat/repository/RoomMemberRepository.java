package me.ramos.WaitForm.domain.chat.repository;

import me.ramos.WaitForm.domain.chat.entity.Room;
import me.ramos.WaitForm.domain.chat.entity.RoomMember;
import me.ramos.WaitForm.domain.chat.repository.jdbc.RoomMemberJdbcRepository;
import me.ramos.WaitForm.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long>, RoomMemberJdbcRepository {

    List<RoomMember> findAllByMemberIn(List<Member> members);

    List<RoomMember> findAllByRoomIdIn(List<Long> roomsIds);

    List<RoomMember> findAllByRoom(Room room);

    @Query("select r from RoomMember r join fetch r.member where r.room.id = :roomId")
    List<RoomMember> findAllWithMemberByRoomId(@Param("roomId") Long roomId);
}
