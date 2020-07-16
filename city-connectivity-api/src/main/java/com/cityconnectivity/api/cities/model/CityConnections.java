package com.cityconnectivity.api.cities.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CityConnections extends BaseModel {

	private static final long serialVersionUID = 7947454079922087984L;

	private City origin;
	private City destination;
}
