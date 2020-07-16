package com.cityconnectivity.api.cities.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ConnectedResponse extends BaseModel {

	private static final long serialVersionUID = -1547285613885101779L;

	private String origin;
	private String destination;
	private String isConnected;
}
