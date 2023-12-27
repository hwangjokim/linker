package com.hwangjo.linker.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class LinkRequest {
    private String link;
    private String description;
    private UUID folderId;

    @Override
    public String toString() {
        return "LinkRequest{" +
                "link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", folderId=" + folderId +
                '}';
    }
}
