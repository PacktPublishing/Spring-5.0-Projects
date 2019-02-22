package com.packt.external;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;
import com.packt.model.CountryGDP;

@RunWith(SpringRunner.class)
@SpringJUnitConfig( classes = {WorldBankApiClient.class})
public class WorldBankApiClientTest {

	@Autowired WorldBankApiClient worldBankClient;
	
	@Test public void testGetGDP() {
		List<CountryGDP> gdpData = worldBankClient.getGDP("IN");
		assertThat(gdpData).hasSize(11);
		CountryGDP gdp = gdpData.get(0);
		assertThat(gdp.getYear()).isEqualTo(Short.valueOf("2017"));
		gdp = gdpData.get(10);
		assertThat(gdp.getYear()).isEqualTo(Short.valueOf("2007"));
	}
}
