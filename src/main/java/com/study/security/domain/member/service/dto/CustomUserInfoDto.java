package com.study.security.domain.member.service.dto;

import com.study.security.domain.member.entity.Member;
import com.study.security.domain.member.enums.Role;
import lombok.Builder;

@Builder
public record CustomUserInfoDto(
    Long memberId,
    String email,
    String password,
    Role role
) {

  public static CustomUserInfoDto of(Member member) {
    return CustomUserInfoDto.builder()
        .memberId(member.getId())
        .email(member.getEmail())
        .password(member.getPassword())
        .role(member.getRole())
        .build();
  }
}
