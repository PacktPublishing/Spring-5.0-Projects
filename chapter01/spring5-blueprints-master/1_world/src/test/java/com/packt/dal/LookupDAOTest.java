package com.packt.dal;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;
import com.packt.config.TestDBConfiguration;

@RunWith(SpringRunner.class)
@SpringJUnitConfig( classes = {TestDBConfiguration.class, LookupDAO.class})
public class LookupDAOTest {

	@Autowired LookupDAO lookupDao;
	
	@Test public void test_getContinents() {
		List<String> continents = lookupDao.getContinents();
		assertThat(continents).hasSize(7);
		assertThat(continents.get(0)).isEqualTo("Africa");
	}
	
	@Test public void test_getRegions() {
		List<String> regions = lookupDao.getRegions();
		assertThat(regions).hasSize(25);
		assertThat(regions.get(0)).isEqualTo("Antarctica");
	}
	
	
}
