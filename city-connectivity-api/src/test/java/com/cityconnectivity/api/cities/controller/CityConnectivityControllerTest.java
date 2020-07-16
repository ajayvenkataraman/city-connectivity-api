package com.cityconnectivity.api.cities.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cityconnectivity.api.cities.controller.CityConnectivityController;
import com.cityconnectivity.api.cities.model.ConnectedResponse;
import com.cityconnectivity.api.cities.service.CityConnectivityService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CityConnectivityController.class)
public class CityConnectivityControllerTest {

	private static final String VALID_REQUEST_TRUE_URI = "/connected?origin=testCityA&destination=testCityB";
	private static final String VALID_REQUEST_FALSE_URI = "/connected?origin=testCityC&destination=testCityD";
	private static final String INVALID_REQUEST_TRUE_URI = "/connected?origin=testCity123&destination=testCity*";
	private static final String INVALID_REQUEST_TRUE_URI_BLANKS = "/connected?origin=&destination=";

	private static final String ORIGIN_ERROR_MESSAGE = "Origin value must contain only alphabets and spaces.";
	private static final String DEST_ERROR_MESSAGE = "Destination value must contain only alphabets and spaces.";
	private static final String ORIGIN_BLANK_MESSAGE = "Origin is required.";
	private static final String DEST_BLANK_MESSAGE = "Destination is required.";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CityConnectivityService mockCityConnectivityService;

	private ConnectedResponse expectedTrueResponse;
	private ConnectedResponse expectedFalseResponse;

	@Before
	public void setup() throws IOException {

		expectedTrueResponse = new ConnectedResponse("testCityA", "testCityB", "Yes");
		expectedFalseResponse = new ConnectedResponse("testCityC", "testCityD", "No");
	}

	@Test
	public void testGetConnectivityInformation() throws Exception {

		Mockito.when(mockCityConnectivityService.isConnected(Mockito.anyString(), Mockito.anyString(), Mockito.any()))
				.thenReturn(true);

		RequestBuilder builderForTrueResponse = MockMvcRequestBuilders.get(VALID_REQUEST_TRUE_URI)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult resultForTrueResponse = mockMvc.perform(builderForTrueResponse).andReturn();

		assertThat(resultForTrueResponse.getResponse().getStatus(), is(HttpStatus.OK.value()));
		assertThat(resultForTrueResponse.getResponse().getContentAsString()
				.equals(objectMapper.writeValueAsString(expectedTrueResponse)), is(true));

		Mockito.when(mockCityConnectivityService.isConnected(Mockito.anyString(), Mockito.anyString(), Mockito.any()))
				.thenReturn(false);

		RequestBuilder builderForFalseResponse = MockMvcRequestBuilders.get(VALID_REQUEST_FALSE_URI)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult resultForFalseResponse = mockMvc.perform(builderForFalseResponse).andReturn();

		assertThat(resultForFalseResponse.getResponse().getStatus(), is(HttpStatus.OK.value()));
		assertThat(resultForFalseResponse.getResponse().getContentAsString()
				.equals(objectMapper.writeValueAsString(expectedFalseResponse)), is(true));

	}

	@Test
	public void testGetConnectivityInformation_ExpectConstraintViolations() throws Exception {

		Mockito.when(mockCityConnectivityService.isConnected(Mockito.anyString(), Mockito.anyString(), Mockito.any()))
				.thenReturn(true);

		RequestBuilder builderForInvalidCase = MockMvcRequestBuilders.get(INVALID_REQUEST_TRUE_URI)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult resultForInvalidCase = mockMvc.perform(builderForInvalidCase).andReturn();

		assertThat(resultForInvalidCase.getResponse().getStatus(), is(HttpStatus.BAD_REQUEST.value()));
		assertThat(resultForInvalidCase.getResponse().getErrorMessage().contains(ORIGIN_ERROR_MESSAGE), is(true));
		assertThat(resultForInvalidCase.getResponse().getErrorMessage().contains(DEST_ERROR_MESSAGE), is(true));

		RequestBuilder builderForBlankCase = MockMvcRequestBuilders.get(INVALID_REQUEST_TRUE_URI_BLANKS)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult resultForBlankCase = mockMvc.perform(builderForBlankCase).andReturn();

		assertThat(resultForBlankCase.getResponse().getStatus(), is(HttpStatus.BAD_REQUEST.value()));
		assertThat(resultForBlankCase.getResponse().getErrorMessage().contains(ORIGIN_BLANK_MESSAGE), is(true));
		assertThat(resultForBlankCase.getResponse().getErrorMessage().contains(DEST_BLANK_MESSAGE), is(true));
	}
}
