package com.cityconnectivity.api.cities.service;

import java.io.IOException;

import org.springframework.core.io.Resource;

public interface CityConnectivityService {

	boolean isConnected(String origin, String destination, Resource cityFileResource) throws IOException;
}
