package com.hwangjo.linker.repository;

import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FolderRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FolderRepository folderRepository;

    @Test
    void findAllByOwner() {
        Member member = memberRepository.findByUsername("testid").get();

        List<Folder> all = folderRepository.findAllByOwner(member);
        Assertions.assertThat(all.size()).isEqualTo(2);
    }


    @Test
    @Commit
    void save(){
        Member member = memberRepository.findByUsername("testid").get();
        Folder testFolder1 = Folder.builder()
                .folderName("TestFolder2")
                .isShared(false)
                .owner(member).build();
        Folder save = folderRepository.save(testFolder1);

        Assertions.assertThat(save).isEqualTo(testFolder1);

    }
}