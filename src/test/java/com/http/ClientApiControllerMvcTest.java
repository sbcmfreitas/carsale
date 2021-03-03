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
import io.carsale.api.ClientApiController;
import io.carsale.dto.ClientRequest;
import io.carsale.dto.ClientResponse;
import io.carsale.model.Client;
import io.carsale.repository.CarRespositoryImpl;
import io.carsale.repository.OrderRespositoryImpl;
import io.carsale.repository.interfaces.BrandRespository;
import io.carsale.repository.interfaces.ClientRespository;
import io.carsale.service.interfaces.CarService;
import io.carsale.service.interfaces.ClientService;
import io.carsale.service.interfaces.OrderService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CarSaleSpringBootApi.class })
@WebMvcTest(controllers = ClientApiController.class)
public class ClientApiControllerMvcTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ClientApiController carApiController;

	@MockBean
	ClientRespository clientRespository;

	@MockBean
	ClientService clientService;

	@MockBean
	CarService carService;

	@MockBean
	OrderService orderService;

	@MockBean
	BrandRespository brandRepository;

	@MockBean
	CarRespositoryImpl carRespositoryImpl;

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
     * GetClients
     **/
	@Test
	public void GetClients_NoneParameters_ShouldBeReturnListOfClientResponse() throws Exception {

		// asset
		List<ClientResponse> clientResponseListMock = Arrays.asList(this.fixture.create(ClientResponse.class));

        when(clientService.findAll()).thenReturn(clientResponseListMock);

		// when
		MvcResult result = mockMvc.perform(get("/client")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		// then
		List<ClientResponse> clientResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ClientResponse>>(){});

		assertFalse(clientResponseList.isEmpty());
		assertEquals(clientResponseListMock.stream().findFirst().get().getId(),clientResponseList.stream().findFirst().get().getId());
	}

	@Test(expected = Exception.class)
	public void GetClients_NoneParameters_ShouldBeReturnException() throws Exception {

		// asset
		given(clientService.findAll()).willAnswer(invocation -> { throw new Exception();});

		// when
		mockMvc.perform(get("/client")
		        .accept(MediaType.APPLICATION_JSON))
				.andReturn();

	}


	/**
     * FindClientById
     **/
	@Test
	public void FindClientById_ValidParameter_ShouldBeReturnClientResponse() throws Exception {

		// asset
		ClientResponse clientResponse = this.fixture.create(ClientResponse.class);

        when(clientService.find(any(Long.class))).thenReturn(clientResponse);

		// when
		MvcResult result = mockMvc.perform(get("/client/1")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		// then
		ClientResponse clientResult = objectMapper.readValue(result.getResponse().getContentAsString(),ClientResponse.class);

		assertNotNull(clientResult);
		assertEquals(clientResponse.getId(),clientResult.getId());
	}

	@Test
	public void FindClientById_NotExistIdParameter_ShouldBeReturnNotFoundStatus() throws Exception {

		// asset
        when(clientService.find(any(Long.class))).thenThrow(new EntityNotFoundException());

		// when
		mockMvc.perform(get("/client/1")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();

	}

	@Test(expected = Exception.class)
	public void FindClientById_NoneParameters_ShouldBeReturnException() throws Exception {

		// asset
		given(clientService.find(any(Long.class))).willAnswer(invocation -> { throw new Exception();});

		// when
		mockMvc.perform(get("/client/1")
		        .accept(MediaType.APPLICATION_JSON))
				.andReturn();

	}



	/**
     * CreateClient
     **/
	@Test
	public void CreateClient_ValidParameters_ShouldBeReturnSuccessStatus() throws Exception {

		// asset
		ClientRequest clientRequest   = this.fixture.create(ClientRequest.class);
		ClientResponse clientResponse = this.fixture.create(ClientResponse.class);
		
        when(this.clientService.create(any(Client.class))).thenReturn(clientResponse);

		// when
		MvcResult result = this.mockMvc.perform(
						  post("/client")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(clientRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andReturn();
		// then
		ClientResponse clientResultResponse = objectMapper.readValue(result.getResponse().getContentAsString(),ClientResponse.class);

		assertNotNull(clientResultResponse.getId());

	}

	@Test
	public void CreateClient_InvalidBrandParameter_ShouldBeReturnBadRequestStatus() throws Exception {

		// asset
		ClientRequest clientRequest   = this.fixture.create(ClientRequest.class);
		           clientRequest.setLastName(null);
		
		ClientResponse clientResponse = this.fixture.create(ClientResponse.class);
		
        when(this.clientService.create(any(Client.class))).thenReturn(clientResponse);

		// when
		this.mockMvc.perform(
						  post("/client")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(clientRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}

	@Test(expected = Exception.class)
	public void CreateClient_NoneParameters_ShouldBeReturnException() throws Exception {

		// asset
		ClientRequest clientRequest   = this.fixture.create(ClientRequest.class);

		given(this.clientService.create(any(Client.class))).willAnswer(invocation -> { throw new Exception();});

		// when
		this.mockMvc.perform(
						  post("/client")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(clientRequest))
				)
	            .andReturn();

	}


	/**
     * UpdateClient
     **/
	@Test
	public void UpdateClient_ValidParameters_ShouldBeReturnAcceptStatus() throws Exception {

		// asset
		ClientRequest clientRequest   = this.fixture.create(ClientRequest.class);
		ClientResponse clientResponse = this.fixture.create(ClientResponse.class);
		
        when(this.clientService.update(any(Client.class))).thenReturn(clientResponse);

		// when
		MvcResult result = this.mockMvc.perform(
						  put("/client")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(clientRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().is(202))
	            .andReturn();
		// then
		ClientResponse clientResultResponse = objectMapper.readValue(result.getResponse().getContentAsString(),ClientResponse.class);

		assertNotNull(clientResultResponse.getId());
	}

	@Test
	public void UpdateClient_InvalidBrandParameter_ShouldBeReturnBadRequestStatus() throws Exception {

		// asset
		ClientRequest clientRequest   = this.fixture.create(ClientRequest.class);
		           clientRequest.setLastName(null);
		
		ClientResponse clientResponse = this.fixture.create(ClientResponse.class);
		
        when(this.clientService.update(any(Client.class))).thenReturn(clientResponse);

		// when
		this.mockMvc.perform(
						  put("/client")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(clientRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}

	@Test(expected = Exception.class)
	public void UpdateClient_NoneParameters_ShouldBeReturnException() throws Exception {

		// asset
		ClientRequest clientRequest   = this.fixture.create(ClientRequest.class);

		given(this.clientService.update(any(Client.class))).willAnswer(invocation -> { throw new Exception();});

		// when
		this.mockMvc.perform(
						  put("/client")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(clientRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();

	}

	@Test
	public void UpdateClient_NotExistClientIdParameter_ShouldBeReturnNotFoundStatus() throws Exception {

		// asset
		ClientRequest clientRequest   = this.fixture.create(ClientRequest.class);
		
        when(this.clientService.update(any(Client.class))).thenThrow(new EntityNotFoundException());

		// when
		this.mockMvc.perform(
						  put("/client")
						 .contentType(MediaType.APPLICATION_JSON)
						 .content(objectMapper.writeValueAsString(clientRequest))
				)
	            .andExpect(MockMvcResultMatchers.status().isNotFound())
	            .andReturn();
	}


	/**
     * DeleteClient
     **/
	@Test
	public void DeleteClient_ValidParameters_ShouldBeReturnNoContentStatus() throws Exception {

		// asset	
        when(this.clientService.delete(any(Long.class))).thenReturn(true);

		// when
		this.mockMvc.perform(
						  delete("/client/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andExpect(MockMvcResultMatchers.status().is(204))
	            .andReturn();
	}

	@Test
	public void DeleteClient_AnyParameters_ShouldBeReturnInternalServerError() throws Exception {

		// asset	
        when(this.clientService.delete(any(Long.class))).thenReturn(false);

		// when
		this.mockMvc.perform(
						  delete("/client/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
	            .andReturn();
	}

	@Test
	public void DeleteClient_NotExistClientIdParameter_ShouldBeReturnNotFoundStatus() throws Exception {

		// asset	
        when(this.clientService.delete(any(Long.class))).thenThrow(new EntityNotFoundException());

		// when
		this.mockMvc.perform(
						  delete("/client/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andExpect(MockMvcResultMatchers.status().isNotFound())
	            .andReturn();
	}

	@Test(expected = Exception.class)
	public void DeleteClient_NoneParameters_ShouldBeReturnException() throws Exception {

		// asset
		given(this.clientService.delete(any(Long.class))).willAnswer(invocation -> { throw new Exception();});

		// when
		this.mockMvc.perform(
						  delete("/client/1")
						 .contentType(MediaType.APPLICATION_JSON)
				)
	            .andReturn();

	}

	
}
