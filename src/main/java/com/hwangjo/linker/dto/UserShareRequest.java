package com.hwangjo.linker.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserShareRequest {
	private String username;
	private UUID folderId;
}
