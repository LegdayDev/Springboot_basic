package com.legday.backboard.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyForm {
    @NotBlank(message = "내용은 필수입니다!")
    private String content;
}
