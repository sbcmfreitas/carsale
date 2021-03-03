package com.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
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
import io.carsale.api.OrderApiController;
import io.carsale.dto.OrderRequest;
import io.carsale.dto.OrderResponse;
import io.carsale.model.Order;
import io.carsale.repository.BrandRespositoryImpl;
import io.carsale.repository.CarRespositoryImpl;
import io.carsale.repository.OrderRespositoryImpl;
import io.carsale.repository.interfaces.ClientRespository;
import io.carsale.repository.interfaces.OrderRespository;
import io.carsale.service.interfaces.CarService;
import io.carsale.service.interfaces.ClientService;
import io.carsale.service.interfaces.OrderService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CarSaleSpringBootApi.class })
@WebMvcTest(controllers = OrderApiController.class)
public class OrderApiControllerMvcTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	OrderApiController carApiController;

	@MockBean
	OrderRespository orderRespository;

	@MockBean
	OrderService orderService;

	@MockBean
	CarService carService;

	@MockBean
	ClientService clientService;

	@MockBean
	ClientRespository brandRepository;

	@MockBean
	CarRespositoryImpl carRespositoryImpl;

	@MockBean
	BrandRespositoryImpl brandRespositoryImpl;

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
     * GetOrders
     **/
	@Test
	public void GetOrders_NoneParameters_ShouldBeReturnListOfOrderResponse() throws Exception {

		// asset
		List<OrderResponse> orderResponseListMock = Arrays.asList(this.fixture.create(OrderResponse.class));

        when(orderService.findAll()).thenReturn(orderResponseListMock);

		// when
		MvcResult result = mockMvc.perform(get("/order")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		// then
		List<OrderResponse> orderResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<OrderResponse>>(){});

		assertFalse(orderResponseList.isEmpty());
		assertEquals(orderResponseListMock.stream().findFirst().get().getId(),orderResponseList.stream().findFirst().get().getId());
	}

	@Test(expected = Exception.class)
	public void GetOrders_ValidParameters_ShouldBeReturnException() throws Exception {

		// asset
		given(orderService.findAll()).willAnswer(invocation -> { throw new Exception();});

		// when
		mockMvc.perform(get("/order")
		        .accept(MediaType.APPLICATION_JSON))
				.andReturn();

	}

	/**
     * FindOrderById
     **/
	@Test
	public void FindOrderById_ValidParameter_ShouldBeReturnOrderResponse() throws Exception {

		// asset
		OrderResponse orderResponse = this.fixture.create(OrderResponse.class);

        when(orderService.find(any(Long.class))).thenReturn(orderResponse);

		// when
		MvcResult result = mockMvc.perform(get("/order/1")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		// then
		OrderResponse orderResult = objectMapper.readValue(result.getResponse().getContentAsString(),OrderResponse.class);

		assertNotNull(orderResult);
		assertEquals(orderResponse.getId(),orderResult.getId());
	}

	@Test
	public void FindOrderById_NotExistIdParameter_ShouldBeReturnNotFoundStatus() throws Exception {

		// asset
        when(orderService.find(any(Long.class))).thenThrow(new EntityNotFoundException());

		// when
		mockMvc.perform(get("/order/1")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();

	}

	@Test(expected = Exception.class)
	public void FindOrderById_ValidParameters_ShouldBeReturnException() throws Exception {

		// asset
		given(orderService.find(any(Long.class))).willAnswer(invocation -> { throw new Exception();});

		// when
		mockMvc.perform(get("/order/1")
		        .accept(MediaType.APPLICATION_JSON))
				.andReturn();

	}



	/**
     * CreateOrder
     **/
	@Test
	public void CreateOrder_ValidParameters_ShouldBeReturnSuccessStatus() throws Exception {

		// asset
		OrderRequest orderRequest   = this.fixture.create(OrderRequest.class);
		OrderResponse orderResponse = this.fixture.create(OrderResponse.class);
		
        when(this.orderService.create(any(Order.class))).thenReturn(orderResponse);

		// when
		MvcResult result = this.mockMvc.perform(
						  post("/order")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(orderRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andReturn();
		// then
		OrderResponse orderResultResponse = objectMapper.readValue(result.getResponse().getContentAsString(),OrderResponse.class);

		assertNotNull(orderResultResponse.getId());

	}

	@Test
	public void CreateOrder_InvalidClientParameter_ShouldBeReturnBadRequestStatus() throws Exception {

		// asset
		OrderRequest orderRequest   = this.fixture.create(OrderRequest.class);
		           orderRequest.setClientId(null);
		
		OrderResponse orderResponse = this.fixture.create(OrderResponse.class);
		
        when(this.orderService.create(any(Order.class))).thenReturn(orderResponse);

		// when
		this.mockMvc.perform(
						  post("/order")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(orderRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}

	@Test(expected = Exception.class)
	public void CreateOrder_ValidParameters_ShouldBeReturnException() throws Exception {

		// asset
		OrderRequest orderRequest   = this.fixture.create(OrderRequest.class);

		given(this.orderService.create(any(Order.class))).willAnswer(invocation -> { throw new Exception();});

		// when
		this.mockMvc.perform(
			post("/order")
		   .contentType(MediaType.APPLICATION_JSON)
		   .content(objectMapper.writeValueAsString(orderRequest))
			)
			.andReturn();

	}


	/**
     * UpdateOrder
     **/
	@Test
	public void UpdateOrder_ValidParameters_ShouldBeReturnAcceptStatus() throws Exception {

		// asset
		OrderRequest orderRequest   = this.fixture.create(OrderRequest.class);
		OrderResponse orderResponse = this.fixture.create(OrderResponse.class);
		
        when(this.orderService.update(any(Order.class))).thenReturn(orderResponse);

		// when
		MvcResult result = this.mockMvc.perform(
						  put("/order")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(orderRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().is(202))
	            .andReturn();
		// then
		OrderResponse orderResultResponse = objectMapper.readValue(result.getResponse().getContentAsString(),OrderResponse.class);

		assertNotNull(orderResultResponse.getId());
	}

	@Test
	public void UpdateOrder_InvalidClientParameter_ShouldBeReturnBadRequestStatus() throws Exception {

		// asset
		OrderRequest orderRequest   = this.fixture.create(OrderRequest.class);
		           orderRequest.setClientId(null);
		
		OrderResponse orderResponse = this.fixture.create(OrderResponse.class);
		
        when(this.orderService.update(any(Order.class))).thenReturn(orderResponse);

		// when
		this.mockMvc.perform(
						  put("/order")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(orderRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}

	@Test
	public void UpdateOrder_NotExistOrderIdParameter_ShouldBeReturnNotFoundStatus() throws Exception {

		// asset
		OrderRequest orderRequest   = this.fixture.create(OrderRequest.class);
		
        when(this.orderService.update(any(Order.class))).thenThrow(new EntityNotFoundException());

		// when
		this.mockMvc.perform(
						  put("/order")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(orderRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isNotFound())
	            .andReturn();
	}

	@Test(expected = Exception.class)
	public void UpdateOrder_ValidParameters_ShouldBeReturnException() throws Exception {

		// asset
		OrderRequest orderRequest   = this.fixture.create(OrderRequest.class);

		given(this.orderService.update(any(Order.class))).willAnswer(invocation -> { throw new Exception();});

		// when
		this.mockMvc.perform(
						  put("/order")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(orderRequest))
				)
	            .andReturn();

	}

	/**
     * DeleteOrder
     **/
	@Test
	public void DeleteOrder_ValidParameters_ShouldBeReturnNoContentStatus() throws Exception {

		// asset	
        when(this.orderService.delete(any(Long.class))).thenReturn(true);

		// when
		this.mockMvc.perform(
						  delete("/order/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andExpect(MockMvcResultMatchers.status().is(204))
	            .andReturn();
	}

	@Test
	public void DeleteOrder_AnyParameters_ShouldBeReturnInternalServerError() throws Exception {

		// asset	
        when(this.orderService.delete(any(Long.class))).thenReturn(false);

		// when
		this.mockMvc.perform(
						  delete("/order/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
	            .andReturn();
	}

	@Test
	public void DeleteOrder_NotExistOrderIdParameter_ShouldBeReturnNotFoundStatus() throws Exception {

		// asset	
        when(this.orderService.delete(any(Long.class))).thenThrow(new EntityNotFoundException());

		// when
		this.mockMvc.perform(
						  delete("/order/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andExpect(MockMvcResultMatchers.status().isNotFound())
	            .andReturn();
	}

	@Test(expected = Exception.class)
	public void DeleteOrder_ValidParameters_ShouldBeReturnException() throws Exception {

		// asset
		given(this.orderService.delete(any(Long.class))).willAnswer(invocation -> { throw new Exception();});

		// when
		this.mockMvc.perform(
						  delete("/order/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andReturn();

	}


}
