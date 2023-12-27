package com.hwangjo.linker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;

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

	@OneToMany(mappedBy = "folder")
	List<Link> links = new ArrayList<>();

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
