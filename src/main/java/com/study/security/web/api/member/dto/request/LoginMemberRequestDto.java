package com.study.security.web.api.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginMemberRequestDto(
    @NotBlank
    String email,
    @NotBlank
    String password
) {

}
