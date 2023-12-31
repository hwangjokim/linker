package com.hwangjo.linker.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folder_id")
	private Folder folder;

	public Board() {
	}

	@Builder
	public Board(String title, String content, LocalDateTime createdAt, Member member) {
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.member = member;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public void updateBoard(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
