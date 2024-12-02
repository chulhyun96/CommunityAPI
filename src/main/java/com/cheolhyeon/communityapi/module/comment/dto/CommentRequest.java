package com.cheolhyeon.communityapi.module.comment.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentRequest {
    @NotBlank(message = "내용을 입력해주세요.")
    @Length(max = 500, message = "최대 500자 까지 가능합니다.")
    private String content;
}