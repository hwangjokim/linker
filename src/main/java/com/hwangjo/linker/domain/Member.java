package com.hwangjo.linker.domain;

import java.time.LocalDate;

import com.hwangjo.linker.enumerate.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity @Getter
public class Member {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String nickname;
	private String password;
	private LocalDate registeredAt;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public Member(String username, String nickname, String password, LocalDate registeredAt, Role role) {
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.registeredAt = registeredAt;
		this.role = role;
	}

	public Member() {
	}
}
