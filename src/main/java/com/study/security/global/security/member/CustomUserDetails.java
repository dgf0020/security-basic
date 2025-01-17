package com.study.security.global.security.member;

import com.study.security.domain.member.service.dto.CustomUserInfoDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
  private final CustomUserInfoDto member;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<String> roles = new ArrayList<>();
    roles.add("ROLE_" + member.role().toString());

    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return member.password();
  }

  @Override
  public String getUsername() {
    return member.email();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
