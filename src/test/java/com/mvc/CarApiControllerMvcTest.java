package com.mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flextrade.jfixture.JFixture;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import io.carsale.CarSaleSpringBootApi;
import io.carsale.api.CarApiController;
import io.carsale.dto.CarResponse;
import io.carsale.repository.ClientRespositoryImpl;
import io.carsale.repository.OrderRespositoryImpl;
import io.carsale.repository.interfaces.BrandRespository;
import io.carsale.repository.interfaces.CarRespository;
import io.carsale.service.interfaces.CarService;
import io.carsale.service.interfaces.ClientService;
import io.carsale.service.interfaces.OrderService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CarSaleSpringBootApi.class })
@WebMvcTest(controllers = CarApiController.class)
public class CarApiControllerMvcTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	CarApiController carApiController;

	@MockBean
	CarRespository carRespository;

	@MockBean
	CarService carService;

	@MockBean
	ClientService clientService;

	@MockBean
	OrderService orderService;

	@MockBean
	BrandRespository brandRepository;

	@MockBean
	ClientRespositoryImpl clientRespositoryImpl;

	@MockBean
	OrderRespositoryImpl orderRespositoryImpl;
	

	JFixture fixture;

	@Before
	public void setup() throws Exception {

		this.fixture = new JFixture();
		this.fixture.customise().circularDependencyBehaviour().omitSpecimen(); // omit circular dependencies
		this.fixture.customise().noResolutionBehaviour().omitSpecimen(); // omit methods that cannot be resolved

	}

	@Test
	public void GetCars_NoneParameters_ReturnListOfCarResponse() throws Exception {

		// asset
		List<CarResponse> carResponseListMock = Arrays.asList(this.fixture.create(CarResponse.class));

        when(carService.findAll()).thenReturn(carResponseListMock);

		// when
		MvcResult result = mockMvc.perform(get("/car")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		// then
		List<CarResponse> carResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CarResponse>>(){});

		assertFalse(carResponseList.isEmpty());
		assertEquals(carResponseListMock.stream().findFirst().get().getId(),carResponseList.stream().findFirst().get().getId());
		assertEquals(carResponseListMock.stream().findFirst().get().getBrand().getId(),carResponseList.stream().findFirst().get().getBrand().getId());

	}

	


	
}
