package com.hwangjo.linker;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hwangjo.linker.domain.Folder;

import groovy.util.logging.Slf4j;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Slf4j
class SpeedTest {

	@PersistenceContext
	private EntityManager em;

	@BeforeEach
	void init(){
		for(int i=0; i<100000; i++){
			Folder build = Folder.builder()
				.folderName("폴더이름")
				.owner(null)
				.shareStatus(false)
				.build();
			em.persist(build);
		}
	}

	// @Test
	void update(){
		long start = System.currentTimeMillis();
		em.createQuery("update Folder d set d.shareStatus=true where folderName = '폴더이름'").executeUpdate();
		long end = System.currentTimeMillis();
		System.out.println(" update : (end - start)  = " + (end - start) );
	}

	// @Test
	void delete(){
		long start = System.currentTimeMillis();
		em.createQuery("delete from Folder d where folderName = '폴더이름'").executeUpdate();
		long end = System.currentTimeMillis();
		System.out.println(" delete : (end - start)  = " + (end - start) );
	}
}
