package me.ramos.WaitForm.domain.chat.repository;

import me.ramos.WaitForm.domain.chat.entity.Message;
import me.ramos.WaitForm.domain.chat.entity.Room;
import me.ramos.WaitForm.domain.chat.repository.querydsl.MessageQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageQuerydslRepository {

    List<Message> findAllByRoom(Room room);

    @Query("select m from Message m join fetch m.room where m.id = :id")
    Optional<Message> findWithRoomById(@Param("id") Long id);
}
