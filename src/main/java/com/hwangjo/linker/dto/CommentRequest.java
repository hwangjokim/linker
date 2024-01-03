package com.hwangjo.linker.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentRequest {
	private String content;
	private Long boardId;
}
