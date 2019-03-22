package com.nilangpatel.test.external;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.nilangpatel.worldgdp.external.WorldBankApiClient;
import com.nilangpatel.worldgdp.model.CountryGDP;

@RunWith(SpringRunner.class)
@SpringJUnitConfig( classes = {WorldBankApiClient.class})
public class WorldBankApiClientTest {

	@Autowired WorldBankApiClient worldBankApiClient;
	
	@Test public void testGetGDP() throws ParseException {
		List<CountryGDP> gdpData = worldBankApiClient.getGDP("IN");
		assertThat(gdpData).hasSize(11);
		CountryGDP gdp = gdpData.get(0);
		assertThat(gdp.getYear()).isEqualTo(Short.valueOf("2018"));
		gdp = gdpData.get(10);
		assertThat(gdp.getYear()).isEqualTo(Short.valueOf("2008"));
	}
}
