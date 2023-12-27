package com.hwangjo.linker.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class ShareRequest {
    private UUID folderId;
    private Boolean shareStatus = false;

    @Override
    public String toString() {
        return "ShareRequest{" +
                "folderId=" + folderId +
                ", shareStatus=" + shareStatus +
                '}';
    }
}
