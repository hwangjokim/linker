package com.hwangjo.linker.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.enumerate.Role;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder encoder;
	@Test
	@Commit
	void register(){
		Member member = Member.builder()
			.userId("testid")
			.password(encoder.encode("password"))
			.nickname("testNick")
			.registeredAt(LocalDate.now())
			.role(Role.ROLE_ADMIN)
			.build();
		Member saved = memberRepository.save(member);
		Assertions.assertThat(saved).isEqualTo(member);
	}
}