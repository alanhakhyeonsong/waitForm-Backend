package me.ramos.WaitForm.repository;

import me.ramos.WaitForm.domain.member.entity.Authority;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void init() {
        Member member = new Member("test@test1.com", "password1234", "Ramos", Authority.ROLE_USER);
        memberRepository.save(member);
    }

    @Test
    @DisplayName("이메일을 기준으로 찾기 테스트")
    public void testFindByEmail() throws Exception {
        //when
        Member member = memberRepository.findByEmail("test@test1.com").orElseThrow();

        //then
        assertEquals("test@test1.com", member.getEmail());
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }
}