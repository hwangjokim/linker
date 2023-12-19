package com.hwangjo.linker.config.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.hwangjo.linker.domain.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter
public class CustomUser extends User {
	private Member member;

	public CustomUser(Member member) {
		super(member.getUsername(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
		log.info("SecurityUser member.username = {}", member.getUsername());
		log.info("SecurityUser member.password = {}", member.getPassword());
		this.member = member;
	}
}
