package com.hwangjo.linker.dto;


import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Link;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SharedFolderResponse {
    private String folderName;
    private List<Link> links;

    public SharedFolderResponse(String folderName, List<Link> links) {
        this.folderName = folderName;
        this.links = links;
    }

    public static SharedFolderResponse from(Folder folder){
        return new SharedFolderResponse(folder.getFolderName(), folder.getLinks());
    }
}
