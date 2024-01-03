package com.hwangjo.linker.exception;

public enum ErrorMessage {
	BLANK_SPACE_EXIST("비어있는 항목이 있습니다."),
	PASSWORD_PATTERN_MISMATCH("비밀번호 조건에 부합하지 않습니다."),
	USERNAME_ALREADY_EXIST("이미 존재하는 유저이름입니다."),
	REGISTER_PASSWORD_MISMATCH("비밀번호가 확인과 일치하지 않습니다.");

	private final String message;

	ErrorMessage(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
