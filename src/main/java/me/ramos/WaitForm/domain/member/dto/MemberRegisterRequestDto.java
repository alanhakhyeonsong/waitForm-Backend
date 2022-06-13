package me.ramos.WaitForm.domain.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.entity.Authority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {

    @ApiModelProperty(value = "이메일", example = "test@test.com", required = true)
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일의 형식이 맞지 않습니다.")
    private String email;

    @ApiModelProperty(value = "비밀번호", example = "password1234", required = true)
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @ApiModelProperty(value = "닉네임", example = "Ramos", required = true)
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .email(getEmail())
                .password(getPassword()) // AuthService에서 BCryptPasswordEncoder로 인코딩 후 save 해야 함.
                .nickname(getNickname())
                .authority(Authority.ROLE_USER)
                .build();
    }
}
