package me.ramos.WaitForm.domain.chat.repository;

import me.ramos.WaitForm.domain.chat.entity.JoinRoom;
import me.ramos.WaitForm.domain.chat.entity.Room;
import me.ramos.WaitForm.domain.chat.repository.jdbc.JoinRoomJdbcRepository;
import me.ramos.WaitForm.domain.chat.repository.querydsl.JoinRoomQuerydslRepository;
import me.ramos.WaitForm.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JoinRoomRepository extends JpaRepository<JoinRoom, Long>, JoinRoomJdbcRepository, JoinRoomQuerydslRepository {

    List<JoinRoom> findByRoomAndMemberIn(Room room, List<Member> members);

    Optional<JoinRoom> findByMemberAndRoom(Member member, Room room);

    @Query("select j from JoinRoom j join fetch j.message where j.room.id = :id")
    List<JoinRoom> findAllWithMessageByRoomId(@Param("id") Long id);

    @Query("select j from JoinRoom j join fetch j.member where j.room.id = :id")
    List<JoinRoom> findAllWithMemberByRoomId(@Param("id") Long id);
}
