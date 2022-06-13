package me.ramos.WaitForm.domain.chat.repository.jdbc;

import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.chat.entity.Room;
import me.ramos.WaitForm.domain.member.entity.Member;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class RoomMemberJdbcRepositoryImpl implements RoomMemberJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllBatch(Room room, List<Member> members) {
        final String sql = "INSERT INTO room_members (`member_id`, `room_id`) VALUES(?, ?)";

        jdbcTemplate.batchUpdate(
                sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, members.get(i).getId().toString());
                        ps.setString(2, room.getId().toString());
                    }

                    @Override
                    public int getBatchSize() {
                        return members.size();
                    }
                });
    }
}
