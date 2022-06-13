package me.ramos.WaitForm.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.member.dto.MemberLoginRequestDto;
import me.ramos.WaitForm.domain.member.dto.MemberRegisterRequestDto;
import me.ramos.WaitForm.domain.member.dto.MemberResponseDto;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.entity.RefreshToken;
import me.ramos.WaitForm.domain.member.exception.MemberAlreadyLogoutException;
import me.ramos.WaitForm.domain.member.exception.MemberEmailAlreadyExistException;
import me.ramos.WaitForm.domain.member.exception.MemberNicknameAlreadyExistException;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import me.ramos.WaitForm.domain.member.repository.RefreshTokenRepository;
import me.ramos.WaitForm.global.config.firebase.FirebaseService;
import me.ramos.WaitForm.global.config.jwt.TokenProvider;
import me.ramos.WaitForm.global.config.jwt.dto.TokenDto;
import me.ramos.WaitForm.global.config.jwt.dto.TokenRequestDto;
import me.ramos.WaitForm.global.error.exception.RefreshTokenInvalidException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final FirebaseService firebaseService;

    @Transactional
    public MemberResponseDto signup(MemberRegisterRequestDto memberRegisterRequestDto) throws Exception {
        if (memberRepository.existsByEmail(memberRegisterRequestDto.getEmail())) {
            throw new MemberEmailAlreadyExistException();
        }

        if (memberRepository.existsByNickname(memberRegisterRequestDto.getNickname())) {
            throw new MemberNicknameAlreadyExistException();
        }

        Member member = memberRegisterRequestDto.toEntity();
        String encryptedPassword = bCryptPasswordEncoder.encode(member.getPassword());
        member.setEncryptedPassword(encryptedPassword);
        Member save = memberRepository.save(member);
        if (save.getId() > 500) {
            me.ramos.WaitForm.global.config.firebase.Member firebase = new me.ramos.WaitForm.global.config.firebase.Member();
            firebase.setId(save.getId());
            firebaseService.insertMember(firebase);
        }

        return MemberResponseDto.of(save);
    }

    @Transactional
    public TokenDto login(MemberLoginRequestDto loginRequest) {
        // Login Email/Password를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        // 실제로 검증이 이루어지는 부분(비밀번호 체크)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken); // 향 후 Redis로 변경 예정

        return tokenDto;
    }


    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RefreshTokenInvalidException();
        }

        // Access Token에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 저장소에서 Member ID를 기반으로 Refresh Token 가져오기
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(MemberAlreadyLogoutException::new);

        // Refresh Token 검증
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RefreshTokenInvalidException();
        }

        // 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 저장소 정보 업데이트 (Redis로 이전 시 변경해야 함)
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    @Transactional
    public void logout(String token) {
        // Refresh Token 검증
        if (!tokenProvider.validateToken(token)) {
            throw new RefreshTokenInvalidException();
        }

        final String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        // InvalidJwtException Customizing 해야함
        RefreshToken refreshToken = refreshTokenRepository.findByKey(memberId).orElseThrow(RuntimeException::new);
        refreshTokenRepository.delete(refreshToken);
    }
}
