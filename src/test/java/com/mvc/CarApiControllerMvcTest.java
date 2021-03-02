package com.mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

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
import io.carsale.dto.CarRequest;
import io.carsale.dto.CarResponse;
import io.carsale.exception.OptionQuantityRestrictionException;
import io.carsale.model.Car;
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

	/**
     * GetCars
     **/
	@Test
	public void GetCars_NoneParameters_ShouldBeReturnListOfCarResponse() throws Exception {

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
	}


	/**
     * FindCarById
     **/
	@Test
	public void FindCarById_ValidParameter_ShouldBeReturnCarResponse() throws Exception {

		// asset
		CarResponse carResponse = this.fixture.create(CarResponse.class);

        when(carService.find(any(Long.class))).thenReturn(carResponse);

		// when
		MvcResult result = mockMvc.perform(get("/car/1")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		// then
		CarResponse carResult = objectMapper.readValue(result.getResponse().getContentAsString(),CarResponse.class);

		assertNotNull(carResult);
		assertEquals(carResponse.getId(),carResult.getId());
	}

	@Test
	public void FindCarById_NotExistIdParameter_ShouldBeReturnNotFoundStatus() throws Exception {

		// asset
        when(carService.find(any(Long.class))).thenThrow(new EntityNotFoundException());

		// when
		mockMvc.perform(get("/car/1")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();

	}


	/**
     * CreateCar
     **/
	@Test
	public void CreateCar_ValidParameters_ShouldBeReturnSuccessStatus() throws Exception {

		// asset
		CarRequest carRequest   = this.fixture.create(CarRequest.class);
		CarResponse carResponse = this.fixture.create(CarResponse.class);
		
        when(this.carService.create(any(Car.class))).thenReturn(carResponse);

		// when
		MvcResult result = this.mockMvc.perform(
						  post("/car")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(carRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andReturn();
		// then
		CarResponse carResultResponse = objectMapper.readValue(result.getResponse().getContentAsString(),CarResponse.class);

		assertNotNull(carResultResponse.getId());

	}

	@Test
	public void CreateCar_InvalidBrandParameter_ShouldBeReturnBadRequestStatus() throws Exception {

		// asset
		CarRequest carRequest   = this.fixture.create(CarRequest.class);
		           carRequest.setBrandId(null);
		
		CarResponse carResponse = this.fixture.create(CarResponse.class);
		
        when(this.carService.create(any(Car.class))).thenReturn(carResponse);

		// when
		this.mockMvc.perform(
						  post("/car")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(carRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}


	@Test
	public void CreateCar_InvalidNumberOfOptions_ShouldBeReturnBadRequest() throws Exception {

		// asset
		CarRequest carRequest= this.fixture.create(CarRequest.class);
		
        when(this.carService.create(any(Car.class))).thenThrow(new OptionQuantityRestrictionException(1,"Option Quantity Exception"));

		// when
		this.mockMvc.perform(
						  post("/car")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(carRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}


	/**
     * UpdateCar
     **/
	@Test
	public void UpdateCar_ValidParameters_ShouldBeReturnAcceptStatus() throws Exception {

		// asset
		CarRequest carRequest   = this.fixture.create(CarRequest.class);
		CarResponse carResponse = this.fixture.create(CarResponse.class);
		
        when(this.carService.update(any(Car.class))).thenReturn(carResponse);

		// when
		MvcResult result = this.mockMvc.perform(
						  put("/car")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(carRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().is(202))
	            .andReturn();
		// then
		CarResponse carResultResponse = objectMapper.readValue(result.getResponse().getContentAsString(),CarResponse.class);

		assertNotNull(carResultResponse.getId());
	}

	@Test
	public void UpdateCar_InvalidBrandParameter_ShouldBeReturnBadRequestStatus() throws Exception {

		// asset
		CarRequest carRequest   = this.fixture.create(CarRequest.class);
		           carRequest.setBrandId(null);
		
		CarResponse carResponse = this.fixture.create(CarResponse.class);
		
        when(this.carService.update(any(Car.class))).thenReturn(carResponse);

		// when
		this.mockMvc.perform(
						  put("/car")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(carRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}

	@Test
	public void UpdateCar_NotExistCarIdParameter_ShouldBeReturnNotFoundStatus() throws Exception {

		// asset
		CarRequest carRequest   = this.fixture.create(CarRequest.class);
		
        when(this.carService.update(any(Car.class))).thenThrow(new EntityNotFoundException());

		// when
		this.mockMvc.perform(
						  put("/car")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(carRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isNotFound())
	            .andReturn();
	}


	@Test
	public void UpdateCar_InvalidNumberOfOptions_ShouldBeReturnBadRequest() throws Exception {

		// asset
		CarRequest carRequest= this.fixture.create(CarRequest.class);
		
        when(this.carService.update(any(Car.class))).thenThrow(new OptionQuantityRestrictionException(1,"Option Quantity Exception"));

		// when
		this.mockMvc.perform(
						  put("/car")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(carRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}

	/**
     * DeleteCar
     **/
	@Test
	public void DeleteCar_ValidParameters_ShouldBeReturnNoContentStatus() throws Exception {

		// asset	
        when(this.carService.delete(any(Long.class))).thenReturn(true);

		// when
		this.mockMvc.perform(
						  delete("/car/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andExpect(MockMvcResultMatchers.status().is(204))
	            .andReturn();
	}


	@Test
	public void DeleteCar_NotExistCarIdParameter_ShouldBeReturnNotFoundStatus() throws Exception {

		// asset	
        when(this.carService.delete(any(Long.class))).thenThrow(new EntityNotFoundException());

		// when
		this.mockMvc.perform(
						  delete("/car/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andExpect(MockMvcResultMatchers.status().isNotFound())
	            .andReturn();
	}

	
}
