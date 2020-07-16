package com.cityconnectivity.api.cities.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.cityconnectivity.api.cities.model.City;
import com.cityconnectivity.api.cities.model.CityConnections;
import com.cityconnectivity.api.cities.model.MapCityConnections;

@RunWith(SpringRunner.class)
public class MapCityConnectionsTest {

	private MapCityConnections mappedCityConnections;

	private List<CityConnections> cityConnectionsList;
	private Map<City, LinkedHashSet<City>> connectionsMap;

	private City testCity1;
	private City testCity2;
	private City testCity3;

	@Before
	public void setup() {

		mappedCityConnections = new MapCityConnections();

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
	public void testConnectCities() {

		mappedCityConnections.connectCities(testCity1, testCity2);
		assertThat(CollectionUtils.containsAll(mappedCityConnections.getCityConnectionMap().entrySet(),
				connectionsMap.entrySet()), is(true));

		LinkedList<City> connectedCities = mappedCityConnections.getConnectedCities(testCity1);
		assertThat(connectedCities.getLast().getName(), is(testCity2.getName()));

		connectedCities = mappedCityConnections.getConnectedCities(testCity2);
		assertThat(connectedCities.getLast().getName(), is(testCity1.getName()));
	}

	@Test
	public void testIsConnected() {

		boolean isConnected = mappedCityConnections.isConnected(testCity1, testCity2);
		assertThat(isConnected, is(false));

		mappedCityConnections.connectCities(testCity1, testCity2);
		isConnected = mappedCityConnections.isConnected(testCity1, testCity2);
		assertThat(isConnected, is(true));

		isConnected = mappedCityConnections.isConnected(testCity2, testCity3);
		assertThat(isConnected, is(false));

		mappedCityConnections.connectCities(testCity2, testCity3);
		isConnected = mappedCityConnections.isConnected(testCity3, testCity2);
		assertThat(isConnected, is(true));
	}

	@Test
	public void testGetConnectedCities() {

		List<City> connectedCities = mappedCityConnections.getConnectedCities(testCity1);
		assertThat(connectedCities, is(Collections.emptyList()));

		mappedCityConnections.connectCities(testCity1, testCity2);
		mappedCityConnections.connectCities(testCity1, testCity3);

		connectedCities = mappedCityConnections.getConnectedCities(testCity1);
		assertThat(connectedCities.isEmpty(), is(false));
		assertThat(connectedCities.size(), is(2));
		assertThat(connectedCities.containsAll(Arrays.asList(testCity2, testCity3)), is(true));
	}
}
