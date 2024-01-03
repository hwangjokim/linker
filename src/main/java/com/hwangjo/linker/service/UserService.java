package com.hwangjo.linker.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.dto.RegisterRequest;
import com.hwangjo.linker.enumerate.Role;
import com.hwangjo.linker.exception.ErrorMessage;
import com.hwangjo.linker.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final PasswordEncoder encoder; // BCryptEncoder
	private final MemberRepository repository;

	public ArrayList<String> register(RegisterRequest request) {
		log.info("FolderRequest : {}", request);
		ArrayList<String> result = validateRegister(request);
		if (!result.isEmpty())
			return result;
		Member newMember = Member.builder()
			.username(request.getUsername())
			.password(encoder.encode(request.getPassword()))
			.nickname(request.getNickname())
			.registeredAt(LocalDate.now())
			.role(Role.ROLE_USER)
			.build();
		repository.save(newMember);
		return result;
	}

	private ArrayList<String> validateRegister(RegisterRequest request) {
		ArrayList<String> errors = new ArrayList<>();
		if (request.getUsername().isBlank() || request.getPassword().isBlank() || request.getNickname().isBlank())
			errors.add(ErrorMessage.BLANK_SPACE_EXIST.getMessage());

		boolean isLongEnough = request.getPassword().length() >= 8;
		boolean hasLetter = request.getPassword().matches(".*[a-zA-Z].*");
		boolean hasSpecialChar = request.getPassword().matches(".*[!@#$%^&*?+-=].*");
		if (!isLongEnough || !hasLetter || !hasSpecialChar)
			errors.add(ErrorMessage.PASSWORD_PATTERN_MISMATCH.getMessage());

		if (repository.existsByUsername(request.getUsername()))
			errors.add(ErrorMessage.USERNAME_ALREADY_EXIST.getMessage());

		if (!request.getPassword().equals(request.getPasswordRepeat()))
			errors.add(ErrorMessage.REGISTER_PASSWORD_MISMATCH.getMessage());

		return errors;
	}
}
