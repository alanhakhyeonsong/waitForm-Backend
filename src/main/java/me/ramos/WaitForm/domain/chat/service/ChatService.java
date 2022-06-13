package me.ramos.WaitForm.domain.chat.service;

import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.chat.dto.*;
import me.ramos.WaitForm.domain.chat.entity.JoinRoom;
import me.ramos.WaitForm.domain.chat.entity.Message;
import me.ramos.WaitForm.domain.chat.entity.Room;
import me.ramos.WaitForm.domain.chat.entity.RoomMember;
import me.ramos.WaitForm.domain.chat.exception.ChatRoomNotFoundException;
import me.ramos.WaitForm.domain.chat.exception.JoinRoomNotFoundException;
import me.ramos.WaitForm.domain.chat.repository.JoinRoomRepository;
import me.ramos.WaitForm.domain.chat.repository.MessageRepository;
import me.ramos.WaitForm.domain.chat.repository.RoomMemberRepository;
import me.ramos.WaitForm.domain.chat.repository.RoomRepository;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.exception.MemberNotFoundException;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import me.ramos.WaitForm.global.config.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final MessageRepository messageRepository;
    private final JoinRoomRepository joinRoomRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // 채팅방 생성
    @Transactional
    public ChatRoomCreateResponse createRoom(List<String> nicknames) {
        final Long memberId = SecurityUtil.getCurrentMemberId();
        final Member inviter = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        nicknames.add(inviter.getNickname());
        final List<Member> members = memberRepository.findAllByNicknameIn(nicknames);

        boolean status = false;
        Room room = getRoomByMembers(members);
        if (room == null) {
            status = true;
            room = roomRepository.save(new Room(inviter));
            roomMemberRepository.saveAllBatch(room, members);
        }

        List<MemberInfoDto> memberInfoDtoList = members.stream()
                .map(MemberInfoDto::new)
                .collect(Collectors.toList());

        return new ChatRoomCreateResponse(status, room.getId(), new MemberInfoDto(inviter), memberInfoDtoList);
    }

    private Room getRoomByMembers(List<Member> members) {
        Room room = null;
        final Map<Long, List<RoomMember>> roomMemberMap = roomMemberRepository.findAllByMemberIn(members)
                .stream()
                .collect(Collectors.groupingBy(r -> r.getRoom().getId()));

        final List<Long> roomIds = new ArrayList<>();
        roomMemberMap.forEach((rid, rms) -> {
            if (rms.size() == members.size()) {
                roomIds.add(rid);
            }
        });

        final Map<Long, List<RoomMember>> roomMemberMapGroupByRoomId = roomMemberRepository.findAllByRoomIdIn(roomIds)
                .stream()
                .collect(Collectors.groupingBy(r -> r.getRoom().getId()));

        for (Long roomId : roomMemberMapGroupByRoomId.keySet()) {
            if (roomMemberMapGroupByRoomId.get(roomId).size() == members.size()) {
                room = roomMemberMapGroupByRoomId.get(roomId).get(0).getRoom();
                break;
            }
        }

        return room;
    }

    // 채팅방 목록 조회
    public List<JoinRoomDto> getJoinRooms() {
        final Long memberId = SecurityUtil.getCurrentMemberId();
        final Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<JoinRoomDto> results = joinRoomRepository.findAllJoinRoomDtoByMemberId(member.getId());
        if (results.size() == 0) {
            throw new ChatRoomNotFoundException();
        }

        return results;
    }

    // 메시지 전송
    @Transactional
    public void sendMessage(MessageRequestDto requestDto) {
        final Member sender = memberRepository.findById(requestDto.getSenderId()).orElseThrow(MemberNotFoundException::new);
        final Room room = roomRepository.findById(requestDto.getRoomId()).orElseThrow(ChatRoomNotFoundException::new);
        List<RoomMember> roomMembers = roomMemberRepository.findAllWithMemberByRoomId(room.getId());

        if (roomMembers.stream().noneMatch(r -> r.getMember().getId().equals(sender.getId())))
            throw new JoinRoomNotFoundException();

        final Message message = messageRepository.save(new Message(sender, room, requestDto.getContent()));
        updateRoom(requestDto.getSenderId(), room, roomMembers, message);

        final MessageDto response = new MessageDto(message);
        roomMembers.forEach(r -> messagingTemplate.convertAndSend("/sub/" + r.getMember().getNickname(), response));
    }

    // 채팅방 업데이트
    private void updateRoom(Long senderId, Room room, List<RoomMember> roomMembers, Message message) {
        final List<Member> members = roomMembers.stream()
                .map(RoomMember::getMember)
                .collect(Collectors.toList());

        final Map<Long, JoinRoom> joinRoomMap = joinRoomRepository.findByRoomAndMemberIn(room, members).stream()
                .collect(Collectors.toMap(j -> j.getMember().getId(), j -> j));

        final List<JoinRoom> newJoinRooms = new ArrayList<>();
        final List<JoinRoom> updateJoinRooms = new ArrayList<>();

        for (RoomMember roomMember : roomMembers) {
            final Member member = roomMember.getMember();
            if (joinRoomMap.containsKey(member.getId())) {
                updateJoinRooms.add(joinRoomMap.get(member.getId()));
            } else {
                newJoinRooms.add(new JoinRoom(room, member, message));
            }
        }
        joinRoomRepository.saveAllBatch(newJoinRooms, message);
        joinRoomRepository.updateAllBatch(updateJoinRooms, message);
    }

    // 메시지 조회
    public List<MessageDto> getAllMessages(Long roomId) {
        final Long memberId = SecurityUtil.getCurrentMemberId();

        return messageRepository.findAllMessageDtoByMemberIdAndRoomId(memberId, roomId);
    }
}
