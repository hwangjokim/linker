package com.hwangjo.linker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {
	String username = "";
	String password = "";
	String passwordRepeat = "";
	String nickname = "";

	@Override
	public String toString() {
		return "RegisterRequest{" +
			"username='" + username + '\'' +
			", password='" + password + '\'' +
			", passwordRepeat='" + passwordRepeat + '\'' +
			", nickname='" + nickname + '\'' +
			'}';
	}
}
