package com.study.security.domain.member.entity.repository;

import com.study.security.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Member findMemberByEmail(String email);
}
