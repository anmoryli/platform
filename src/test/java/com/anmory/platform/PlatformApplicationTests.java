package com.anmory.platform;

import com.anmory.platform.GraphService.Dao.Herb;
import com.anmory.platform.GraphService.Repository.HerbRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlatformApplicationTests {

	@Autowired
	HerbRepo herbRepo;
	@Test
	void testFindHerbByName() {
		Herb byName = herbRepo.findByName("雪莲花");
		System.out.println(byName);
	}

}
