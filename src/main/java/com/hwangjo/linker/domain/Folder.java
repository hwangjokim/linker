package com.hwangjo.linker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Builder;

import lombok.Getter;

@Entity
@Getter
public class Folder {
	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String folderName;
	private Boolean shareStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Member owner;

	@OneToMany(mappedBy = "folder")
	List<Link> links = new ArrayList<>();

	@OneToMany(mappedBy = "folder")
	List<FolderShare> shares = new ArrayList<>();

	@Builder
	public Folder(String folderName, boolean shareStatus, Member owner) {
		this.folderName = folderName;
		this.shareStatus = shareStatus;
		this.owner = owner;
	}

	public Folder() {
	}

	public void updateFolderName(String  folderName){
		this.folderName = folderName;
	}

	public void updateShareStatus(Boolean shareStatus){
		this.shareStatus = shareStatus;
	}
}
