package me.ramos.WaitForm.repository;

import me.ramos.WaitForm.config.TestQuerydslConfig;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static me.ramos.WaitForm.support.helper.MemberTestHelper.givenMember;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TestQuerydslConfig.class})
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    @DisplayName("회원 저장 테스트")
    public void testSaveMember() throws Exception {
        //when
        Member savedMember = memberRepository.save(givenMember());

        //then
        assertThat(memberRepository.existsById(savedMember.getId())).isTrue();
    }

    @Test
    @DisplayName("이메일을 기준으로 유저 찾기")
    public void testFindByEmail() throws Exception {
        //given
        Member savedMember = memberRepository.save(givenMember());

        //when
        Optional<Member> memberOptional = memberRepository.findByEmail(savedMember.getEmail());

        //then
        assertThat(memberOptional.get()).isNotNull();
    }

    @Test
    @DisplayName("Id를 기준으로 유저 찾기")
    public void testFindById() throws Exception {
        //given
        Member savedMember = memberRepository.save(givenMember());

        //when
        Optional<Member> result = memberRepository.findById(savedMember.getId());

        //then
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("닉네임을 기준으로 유저 찾기")
    public void testFindByNickname() throws Exception {
        //given
        Member savedMember = memberRepository.save(givenMember());

        //when
        Optional<Member> memberOptional = memberRepository.findByNickname(savedMember.getNickname());

        //then
        assertThat(memberOptional.get()).isNotNull();
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }
}