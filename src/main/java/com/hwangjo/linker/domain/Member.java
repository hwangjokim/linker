package com.hwangjo.linker.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member {
	@Id
	@GeneratedValue
	private Long id;
	private String userId;
	private String nickname;
	private String password;
	private LocalDate registeredAt;
}
