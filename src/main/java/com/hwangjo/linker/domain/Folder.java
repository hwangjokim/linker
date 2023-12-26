package com.hwangjo.linker.domain;

import java.util.UUID;

import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Folder {
	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String folderName;
	private boolean isShared;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Member owner;

	@Builder
	public Folder(String folderName, boolean isShared, Member owner) {
		this.folderName = folderName;
		this.isShared = isShared;
		this.owner = owner;
	}

	public Folder() {
	}

	public void updateFolderName(String  folderName){
		this.folderName = folderName;
	}
}
