package me.ramos.WaitForm.domain.chat.repository;

import me.ramos.WaitForm.domain.chat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
