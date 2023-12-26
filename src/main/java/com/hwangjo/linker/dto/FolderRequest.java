package com.hwangjo.linker.dto;


import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FolderRequest {
    public String folderName;

    public Folder toEntity(Member owner){
        return Folder.builder().
                folderName(this.folderName)
                .owner(owner)
                .isShared(false)
                .build();
    }
}
