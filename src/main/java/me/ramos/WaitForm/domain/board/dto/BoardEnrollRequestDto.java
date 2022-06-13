package me.ramos.WaitForm.domain.board.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardEnrollRequestDto {

    @ApiModelProperty(value = "제목", example = "게시글 제목", required = true)
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @ApiModelProperty(value = "본문", example = "(최소 500자, 최대 4000자 이내로 게시글 작성)", required = true)
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 4000, min = 500, message = "최소 500자, 최대 4000자")
    private String content;

}
