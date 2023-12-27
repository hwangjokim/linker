package com.hwangjo.linker.service;

import com.hwangjo.linker.config.security.CustomUser;
import com.hwangjo.linker.domain.Folder;
import com.hwangjo.linker.domain.Link;
import com.hwangjo.linker.domain.Member;
import com.hwangjo.linker.dto.FolderRequest;
import com.hwangjo.linker.dto.LinkRequest;
import com.hwangjo.linker.dto.RegisterRequest;
import com.hwangjo.linker.repository.FolderRepository;
import com.hwangjo.linker.repository.MemberRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class LinkServiceTest {

    @Autowired
    private LinkService linkService;

    @Autowired
    private FolderService folderService;
    @Autowired
    private UserService userService;

    @Autowired
    private MemberRepository userRepository;

    @Autowired
    FolderRepository folderRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void addNewLink() {
        //give
        RegisterRequest request = new RegisterRequest();
        request.setUsername("linktest");
        request.setPassword("password!");
        request.setNickname("nick");
        request.setPasswordRepeat("password!");
        ArrayList<String> register = userService.register(request);
        register.forEach(System.out::println);
        Assertions.assertThat(register.size()).isEqualTo(0);


        Member member = userRepository.findByUsername("linktest").get();
        CustomUser user = new CustomUser(member);

        FolderRequest folderRequest = new FolderRequest();
        folderRequest.setFolderName("myfolder");
        Folder folder = folderService.addNewFolder(user, folderRequest);

        Assertions.assertThat(folder.getFolderName()).isEqualTo("myfolder");

        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setLink("http://naver.com");
        linkRequest.setDescription("naver");
        linkRequest.setFolderId(folder.getId());

        //when
        Link saved = linkService.addNewLink(user, linkRequest);
        Assertions.assertThat(saved.getDescription()).isEqualTo("naver");

        em.clear();
        //then
        List<Link> links = folderRepository.findById(folder.getId()).get().getLinks();
        links.forEach(System.out::println);

        Assertions.assertThat(links.size())
                .isEqualTo(1);

    }
}