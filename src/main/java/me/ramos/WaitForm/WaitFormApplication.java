package me.ramos.WaitForm;

import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.member.entity.Authority;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.IntStream;

@RequiredArgsConstructor
@SpringBootApplication
public class WaitFormApplication {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(WaitFormApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(MemberRepository memberRepository) throws Exception {
		return (args) -> {
			IntStream.rangeClosed(1, 500).forEach(index ->
					memberRepository.save(Member.builder()
							.email("test"+index+"@naver.com")
							.password(bCryptPasswordEncoder.encode("test"))
							.nickname("test"+index)
							.authority(Authority.ROLE_USER)
							.build())
					);
		};
	}
}
