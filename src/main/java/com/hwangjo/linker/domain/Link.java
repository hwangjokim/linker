package com.hwangjo.linker.domain;

import java.time.LocalDate;
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
public class Link {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String link;
	private String description;

	private LocalDate createdAt;
	private LocalDate lastClickedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folder_id")
	private Folder folder;

	@Builder
	public Link(String link, String description, LocalDate createdAt, LocalDate lastClickedAt, Folder folder) {
		this.link = link;
		this.description = description;
		this.createdAt = createdAt;
		this.lastClickedAt = lastClickedAt;
		this.folder = folder;
	}

	public Link() {
	}
}
