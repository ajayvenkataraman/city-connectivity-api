package com.cityconnectivity.api.cities.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.cityconnectivity.api.cities.model.City;
import com.cityconnectivity.api.cities.model.CityConnections;
import com.cityconnectivity.api.cities.model.MapCityConnections;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CityConnectivityServiceImpl implements CityConnectivityService {

	@Override
	public boolean isConnected(String origin, String destination, Resource cityFileResource) throws IOException {

		MapCityConnections mappedCityConnections = this
				.getMappedCityConnections(this.getCityConnectionsFromFile(cityFileResource));

		LinkedList<City> traversedCities = new LinkedList<>();
		traversedCities.add(new City(origin));

		return traverseAndFindConnections(mappedCityConnections, traversedCities, new City(destination));
	}

	protected boolean traverseAndFindConnections(MapCityConnections mappedCityConnections,
			LinkedList<City> traversedCities, City destination) {

		LinkedList<City> connectedOriginCities = mappedCityConnections.getConnectedCities(traversedCities.getLast());
		boolean isConnected = false;

		for (City city : connectedOriginCities) {

			if (traversedCities.contains(city)) {

				continue;
			}
			traversedCities.add(city);

			if (city.equals(destination)) {

				isConnected = true;
				break;
			}
			isConnected = traverseAndFindConnections(mappedCityConnections, traversedCities, destination);
		}
		return isConnected;
	}

	protected MapCityConnections getMappedCityConnections(List<CityConnections> cityConnectionsList) throws IOException {

		log.debug("List of connected cities: {}", cityConnectionsList);

		MapCityConnections mapCityConnections = new MapCityConnections();
		cityConnectionsList.stream()
				.filter(Objects::nonNull)
				.forEach(connection -> mapCityConnections.connectCities(connection.getOrigin(), connection.getDestination()));

		return mapCityConnections;
	}

	protected List<CityConnections> getCityConnectionsFromFile(Resource cityFileResource) throws IOException {
		
		InputStream cityFileStream = cityFileResource.getInputStream();
		List<String> connectionList = (List<String>) IOUtils.readLines(cityFileStream);

		return connectionList.stream()
				.filter(StringUtils::isNotBlank)
				.filter(conn -> conn.contains(", "))
				.map(conn -> conn.split(", "))
				.map(conn -> new CityConnections(new City(conn[0]), new City(conn[1])))
				.collect(Collectors.toList());
	}
}
