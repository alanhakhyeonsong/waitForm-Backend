package me.ramos.WaitForm.domain.chat.repository.jdbc;

import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.chat.entity.JoinRoom;
import me.ramos.WaitForm.domain.chat.entity.Message;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class JoinRoomJdbcRepositoryImpl implements JoinRoomJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllBatch(List<JoinRoom> joinRooms, Message message) {
        LocalDateTime now = LocalDateTime.now();
        final String sql =
                "INSERT INTO join_rooms (`room_id`, `member_id`, `message_id`, `join_room_created_date`) " +
                "VALUES(?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(
                sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, joinRooms.get(i).getRoom().getId().toString());
                        ps.setString(2, joinRooms.get(i).getMember().getId().toString());
                        ps.setString(3, message.getId().toString());
                        ps.setString(4, String.valueOf(now));
                    }

                    @Override
                    public int getBatchSize() {
                        return joinRooms.size();
                    }
                });
    }

    @Override
    public void updateAllBatch(List<JoinRoom> joinRooms, Message message) {
        final String sql = "UPDATE join_rooms SET message_id = ? where join_room_id = ?";

        jdbcTemplate.batchUpdate(
                sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, message.getId().toString());
                        ps.setString(2, joinRooms.get(i).getId().toString());
                    }

                    @Override
                    public int getBatchSize() {
                        return joinRooms.size();
                    }
                });
    }
}
