package com.hwangjo.linker.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardRequest {
	private String title;
	private String content;
	private UUID folderId;
}
