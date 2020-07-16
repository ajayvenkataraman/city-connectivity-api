package com.cityconnectivity.api.cities.controller;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cityconnectivity.api.cities.model.ConnectedResponse;
import com.cityconnectivity.api.cities.service.CityConnectivityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class CityConnectivityController {

	private static final String YES_STRING_VAL = "Yes";
	private static final String NO_STRING_VAL = "No";

	private final CityConnectivityService cityConnectivityService;

	@Value("classpath:ComponentResources/CityConnections.txt")
	private Resource cityFileResource;

	@GetMapping("/connected")
	public ConnectedResponse getConnectivityInformation(
			@Valid 
			@NotBlank(message = "Origin is required.") 
			@Pattern(regexp = "^[a-zA-Z\\\\s]*$", message = "Origin value must contain only alphabets and spaces.") 
			@RequestParam String origin,
			@Valid 
			@NotBlank(message = "Destination is required.") 
			@Pattern(regexp = "^[a-zA-Z\\\\s]*$", message = "Destination value must contain only alphabets and spaces.") 
			@RequestParam String destination) throws IOException {

		boolean isConnected = cityConnectivityService.isConnected(origin, destination, cityFileResource);

		if (isConnected) {

			return new ConnectedResponse(origin, destination, YES_STRING_VAL);
		}
		return new ConnectedResponse(origin, destination, NO_STRING_VAL);
	}
}
