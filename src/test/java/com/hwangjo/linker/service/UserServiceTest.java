package com.hwangjo.linker.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.dto.RegisterRequest;
import com.hwangjo.linker.exception.ErrorMessage;

@SpringBootTest
@Transactional
class UserServiceTest {

	@Autowired
	private UserService service;
	@Test
	@DisplayName("회원가입_성공")
	void success() {
		//give
		RegisterRequest request = new RegisterRequest();
		request.setUsername("username1");
		request.setPassword("password@");
		request.setPasswordRepeat("password@");
		request.setNickname("mynick");
		//when
		ArrayList<String> result = service.register(request);
		//then
		Assertions.assertThat(result.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("회원가입_실패_중복이름")
	void failureByNickname(){
		//give
		success();
		RegisterRequest request = new RegisterRequest();
		request.setUsername("username1");
		request.setPassword("password@");
		request.setPasswordRepeat("password@");
		request.setNickname("mynick");
		//when

		ArrayList<String> result = service.register(request);
		//then
		Assertions.assertThat(result.get(0)).isEqualTo(ErrorMessage.USERNAME_ALREADY_EXIST.getMessage());
	}

	@Test
	@DisplayName("회원가입_실패_빈칸존재")
	void failureByBlank(){
		//give
		RegisterRequest request = new RegisterRequest();
		request.setUsername("username1");
		request.setPassword("password@");
		request.setPasswordRepeat("password@");
		request.setNickname("");
		//when
		ArrayList<String> result = service.register(request);
		//then
		Assertions.assertThat(result.get(0)).isEqualTo(ErrorMessage.BLANK_SPACE_EXIST.getMessage());
	}

	@Test
	@DisplayName("회원가입_실패_비밀번호_불일치")
	void failureByPasswordMismatch(){
		//give
		RegisterRequest request = new RegisterRequest();
		request.setUsername("username1");
		request.setPassword("password!");
		request.setPasswordRepeat("password@");
		request.setNickname("mynick");
		//when

		ArrayList<String> result = service.register(request);
		//then
		Assertions.assertThat(result.get(0)).isEqualTo(ErrorMessage.REGISTER_PASSWORD_MISMATCH.getMessage());
	}

	@Test
	@DisplayName("회원가입_실패_비밀번호_조건_미충족")
	void failureByPasswordPattern(){
		//give
		RegisterRequest request = new RegisterRequest();
		request.setUsername("username1");
		request.setPassword("pass!");
		request.setPasswordRepeat("pass!");
		request.setNickname("mynick");
		//when

		ArrayList<String> result = service.register(request);
		//then
		Assertions.assertThat(result.get(0)).isEqualTo(ErrorMessage.PASSWORD_PATTERN_MISMATCH.getMessage());
	}
}