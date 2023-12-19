package com.hwangjo.linker.service.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final MemberRepository memberRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("detail : {}", username);
		Optional<Member> member = memberRepository.findByUsername(username);
		return member.map(CustomUser::new).orElseThrow(() -> new UsernameNotFoundException(username));
	}
}
