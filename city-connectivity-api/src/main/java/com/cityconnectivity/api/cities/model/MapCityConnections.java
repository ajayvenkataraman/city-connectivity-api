package com.cityconnectivity.api.cities.model;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MapCityConnections extends BaseModel {

	private static final long serialVersionUID = 4504786616368566723L;

	private Map<City, LinkedHashSet<City>> cityConnectionMap = new HashMap<>();

	public void connectCities(City origin, City destination) {

		addConnection(origin, destination);
		addConnection(destination, origin);
	}

	public boolean isConnected(City origin, City destination) {

		return Objects.isNull(cityConnectionMap.get(origin)) ? false 
				: cityConnectionMap.get(origin).contains(destination);
	}

	public LinkedList<City> getConnectedCities(City origin) {

		return Objects.isNull(cityConnectionMap.get(origin)) ? new LinkedList<>()
				: new LinkedList<City>(cityConnectionMap.get(origin));
	}

	private void addConnection(City cityOne, City cityTwo) {

		LinkedHashSet<City> connection = cityConnectionMap.get(cityOne);

		if (Objects.isNull(connection)) {

			connection = new LinkedHashSet<>();
			cityConnectionMap.put(cityOne, connection);
		}
		connection.add(cityTwo);
	}
}
