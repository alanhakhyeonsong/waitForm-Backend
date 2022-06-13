package me.ramos.WaitForm.domain.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberLogoutRequestDto {

    @ApiModelProperty(value = "refreshToken", required = true)
    @NotBlank
    private String refreshToken;
}
