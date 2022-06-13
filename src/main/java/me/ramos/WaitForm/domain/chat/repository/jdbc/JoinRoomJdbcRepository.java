package me.ramos.WaitForm.domain.chat.repository.jdbc;

import me.ramos.WaitForm.domain.chat.entity.JoinRoom;
import me.ramos.WaitForm.domain.chat.entity.Message;

import java.util.List;

public interface JoinRoomJdbcRepository {

    void saveAllBatch(List<JoinRoom> joinRooms, Message message);

    void updateAllBatch(List<JoinRoom> joinRooms, Message message);
}
