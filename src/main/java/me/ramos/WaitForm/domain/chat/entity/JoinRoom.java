package me.ramos.WaitForm.domain.chat.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ramos.WaitForm.domain.member.entity.Member;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "join_rooms")
public class JoinRoom {

    @Id
    @Column(name = "join_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    @CreatedDate
    @Column(name = "join_room_created_date")
    private LocalDateTime createdDate;

    @Builder
    public JoinRoom(Room room, Member member, Message message) {
        this.room = room;
        this.member = member;
        this.message = message;
        this.createdDate = LocalDateTime.now();
    }
}
