package com.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.flextrade.jfixture.JFixture;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import io.carsale.adapter.ModelMapperFactory;
import io.carsale.dto.CarRequest;
import io.carsale.dto.CarResponse;
import io.carsale.dto.ClientRequest;
import io.carsale.dto.ClientResponse;
import io.carsale.dto.OrderRequest;
import io.carsale.dto.OrderResponse;
import io.carsale.model.Car;
import io.carsale.model.Client;
import io.carsale.model.Order;

@SpringBootTest
public class ModelMapperTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
   
    private ModelMapper modelMapper;

    JFixture fixture;

    @Before
    public void setUp() {

        this.fixture = new JFixture();
        this.fixture.customise().circularDependencyBehaviour().omitSpecimen(); // omit circular dependencies
        this.fixture.customise().noResolutionBehaviour().omitSpecimen(); // omit methods that cannot be resolved

        this.modelMapper = ModelMapperFactory.getInstance();

    }


    @Test
    public void ModelAdapter_CarRequestToCar_ShouldBeReturnCar() {

        // given
        CarRequest carRequest = this.fixture.create(CarRequest.class);

        // when
        Car car = modelMapper.map(carRequest, Car.class);

        // then
        assertEquals(carRequest.getId(),car.getId());
        assertEquals(carRequest.getBrandId(),car.getBrand().getId());
        assertEquals(carRequest.getOptionIds().stream().findFirst().get(),car.getOptions().stream().findFirst().get().getId());
        assertEquals(carRequest.getOptionIds().size(),car.getOptions().size());
        assertEquals(carRequest.getValue(),car.getValue());
    }

    @Test
    public void ModelAdapter_CarToCarResponse_ShouldBeReturnCarResponse() {

        // given
        Car car = this.fixture.create(Car.class);

        // when
        CarResponse carResponse = modelMapper.map(car, CarResponse.class);

        // then
        assertEquals(car.getId(),carResponse.getId());
        assertEquals(car.getBrand().getId(),carResponse.getBrand().getId());
        assertEquals(car.getBrand().getName(),carResponse.getBrand().getName());
        assertEquals(car.getOptions().stream().findFirst().get().getId(),carResponse.getOptions().stream().findFirst().get().getId());
        assertEquals(car.getOptions().size(),carResponse.getOptions().size());
        assertEquals(car.getValue(),carResponse.getValue());
    }

    @Test
    public void ModelAdapter_ClientRequestToClient_ShouldBeReturnClient() {

        // given
        ClientRequest clientRequest = this.fixture.create(ClientRequest.class);

        // when
        Client client = modelMapper.map(clientRequest, Client.class);

        // then
        assertEquals(clientRequest.getId(),client.getId());
        assertEquals(clientRequest.getEmail(),client.getEmail());
        assertEquals(clientRequest.getFirstName(),client.getFirstName());
        assertEquals(clientRequest.getLastName(),client.getLastName());
        assertEquals(clientRequest.getPhone(),client.getPhone());
    }


    @Test
    public void ModelAdapter_ClientToClientResponse_ShouldBeReturnClientResponse() {

        // given
        Client client = this.fixture.create(Client.class);

        // when
        ClientResponse clientResponse = modelMapper.map(client, ClientResponse.class);

        // then
        assertEquals(client.getId(),clientResponse.getId());
        assertEquals(client.getEmail(),clientResponse.getEmail());
        assertEquals(client.getFirstName(),clientResponse.getName());
        assertEquals(client.getPhone(),clientResponse.getPhone());
    }


    @Test
    public void ModelAdapter_OrderRequestToOrder_ShouldBeReturnOrder() {

        // given
        OrderRequest orderRequest = this.fixture.create(OrderRequest.class);

        // when
        Order order = modelMapper.map(orderRequest, Order.class);

        // then
        assertEquals(orderRequest.getId(),order.getId());
        assertEquals(orderRequest.getClientId(),order.getClient().getId());
        assertEquals(orderRequest.getCarId(),order.getCar().getId());
        assertEquals(orderRequest.getOrderDate(),order.getOrderDate());
        assertEquals(orderRequest.getStatus(),order.getStatus());
        assertEquals(orderRequest.getValue(),order.getValue());
    }


    @Test
    public void ModelAdapter_OrderToOrderResponse_ShouldBeReturnOrderResponse() {

        // given
        Order order = this.fixture.create(Order.class);

        // when
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);

        // then
        assertEquals(order.getId(),orderResponse.getId());
        assertEquals(order.getCar().getId(),orderResponse.getCar().getId());
        assertEquals(order.getClient().getId(),orderResponse.getClient().getId());
        assertEquals(order.getOrderDate().toString(),orderResponse.getOrderDate());
        assertEquals(order.getStatus().toString(),orderResponse.getStatus());
        assertEquals(order.getValue(),orderResponse.getValue());
    }


    
 
}
