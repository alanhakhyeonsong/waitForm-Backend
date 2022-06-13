package me.ramos.WaitForm.service;

import me.ramos.WaitForm.domain.member.entity.Authority;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import me.ramos.WaitForm.domain.member.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void test() throws Exception {
        //given
        String rawPassword = "test1234";
        Member member = Member.builder()
                .email("test@test.com")
                .password(bCryptPasswordEncoder.encode(rawPassword))
                .nickname("Ramos")
                .authority(Authority.ROLE_USER)
                .build();

        String userEmail = memberRepository.save(member).getEmail();
        String userNickname = memberRepository.findByEmail(userEmail).orElseThrow().getNickname();

        //when

        //then
        assertEquals("test@test.com", userEmail);
        assertEquals("Ramos", userNickname);
    }
}
