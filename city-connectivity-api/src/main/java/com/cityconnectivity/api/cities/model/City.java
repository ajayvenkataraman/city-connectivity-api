package com.cityconnectivity.api.cities.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class City extends BaseModel {

	private static final long serialVersionUID = -6207945145032937336L;

	private String name;
}