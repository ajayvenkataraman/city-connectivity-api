package com.cityconnectivity.api.cities.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import com.cityconnectivity.api.cities.model.City;
import com.cityconnectivity.api.cities.model.CityConnections;
import com.cityconnectivity.api.cities.model.MapCityConnections;
import com.cityconnectivity.api.cities.service.CityConnectivityServiceImpl;

@RunWith(SpringRunner.class)
public class CityConnectivityServiceImplTest {

	private static final String TEST_RESOURCE_FILE_LOCATION = "classpath:TestResources/TestCityConnections.txt";

	private CityConnectivityServiceImpl cityConnectivityService;
	private ResourceLoader resourceLoader;

	private List<CityConnections> cityConnectionsList;
	private Map<City, LinkedHashSet<City>> connectionsMap;

	private City testCity1;
	private City testCity2;
	private City testCity3;

	@Before
	public void setup() {

		cityConnectivityService = new CityConnectivityServiceImpl();
		resourceLoader = new DefaultResourceLoader();

		testCity1 = new City("testCity1");
		testCity2 = new City("testCity2");
		testCity3 = new City("testCity3");

		cityConnectionsList = new ArrayList<>();
		cityConnectionsList.add(new CityConnections(testCity1, testCity2));

		connectionsMap = new HashMap<>();
		connectionsMap.put(testCity1, new LinkedHashSet<>(Collections.singletonList(testCity2)));
		connectionsMap.put(testCity2, new LinkedHashSet<>(Collections.singletonList(testCity1)));
	}

	@Test
	public void testIsConnected() throws IOException {

		Resource testResource = resourceLoader.getResource(TEST_RESOURCE_FILE_LOCATION);

		boolean isConnected = cityConnectivityService.isConnected("testCity1", "testCity2", testResource);
		assertThat(isConnected, is(true));

		isConnected = cityConnectivityService.isConnected("testCity1", "testCity3", testResource);
		assertThat(isConnected, is(true));

		isConnected = cityConnectivityService.isConnected("testCity3", "testCity2", testResource);
		assertThat(isConnected, is(true));

		isConnected = cityConnectivityService.isConnected("testCity1", "testCity4", testResource);
		assertThat(isConnected, is(false));

		isConnected = cityConnectivityService.isConnected("testCity5", "testCity4", testResource);
		assertThat(isConnected, is(true));
	}

	@Test
	public void testTraverseAndFindConnections() throws IOException {

		LinkedList<City> traversedCities = new LinkedList<>();

		MapCityConnections mappedCityConnections = cityConnectivityService
				.getMappedCityConnections(cityConnectionsList);

		traversedCities.add(testCity3);
		assertThat(
				cityConnectivityService.traverseAndFindConnections(mappedCityConnections, traversedCities, testCity2),
				is(false));

		traversedCities.add(testCity1);
		assertThat(
				cityConnectivityService.traverseAndFindConnections(mappedCityConnections, traversedCities, testCity2),
				is(true));

		traversedCities.add(new City("testCity4"));
		assertThat(
				cityConnectivityService.traverseAndFindConnections(mappedCityConnections, traversedCities, testCity2),
				is(false));
	}

	@Test
	public void testGetMappedCityConnections() throws IOException {

		MapCityConnections mappedCityConnections = cityConnectivityService
				.getMappedCityConnections(cityConnectionsList);

		assertThat(mappedCityConnections, is(notNullValue()));
		assertThat(CollectionUtils.containsAll(mappedCityConnections.getCityConnectionMap().entrySet(),
				connectionsMap.entrySet()), is(true));
	}

	@Test
	public void testGetCityConnectionsFromFile() throws IOException {

		Resource testFileResource = resourceLoader.getResource(TEST_RESOURCE_FILE_LOCATION);
		List<CityConnections> testConnectionsList = cityConnectivityService
				.getCityConnectionsFromFile(testFileResource);

		assertThat(testConnectionsList, is(notNullValue()));
		assertThat(testConnectionsList.size(), is(3));
		assertThat(testConnectionsList.equals(cityConnectionsList), is(false));

		CityConnections testCity3Connection = new CityConnections(testCity3, testCity1);
		cityConnectionsList.add(testCity3Connection);

		assertThat(testConnectionsList.containsAll(cityConnectionsList), is(true));
		cityConnectionsList.remove(testCity3Connection);
	}
}
