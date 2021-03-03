package com.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.flextrade.jfixture.JFixture;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import io.carsale.dto.OrderResponse;
import io.carsale.model.Order;
import io.carsale.repository.interfaces.OrderRespository;
import io.carsale.service.OrderServiceImpl;

@SpringBootTest
public class OrderServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRespository orderRepository;

    JFixture fixture;

    @Before
    public void setUp() {

        this.fixture = new JFixture();
        this.fixture.customise().circularDependencyBehaviour().omitSpecimen(); // omit circular dependencies
        this.fixture.customise().noResolutionBehaviour().omitSpecimen(); // omit methods that cannot be resolved

    }

    // FindAll
    @Test
    public void FindAll_ValidParameters_OrderRepositoryCalled() {

        // given
        List<Order> orderList = Arrays.asList(this.fixture.create(Order.class));

        when(orderRepository.findAll()).thenReturn(orderList);

        // when
        orderService.findAll();

        // then
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void FindAll_ValidParameters_ShoudBeCorrectReturn() {

        // given
        List<Order> orderList = Arrays.asList(this.fixture.create(Order.class));

        when(orderRepository.findAll()).thenReturn(orderList);

        // when
        List<OrderResponse> orderResponseList = orderService.findAll();

        // then
        assertFalse(orderResponseList.isEmpty());

    }

    @Test(expected = Exception.class)
    public void FindAll_ValidParameters_ShoudBeReturnException() {

        // given
        when(orderRepository.findAll()).thenThrow(new Exception());

        // when
        orderService.findAll();

    }

    // Create
    @Test
    public void Create_ValidParameters_OrderRepositoryCalled() throws Exception {

		// given
        Order order = this.fixture.create(Order.class);

		when(orderRepository.save(any(Order.class))).thenReturn(true);

		// when
		orderService.create(order);

		// then
		verify(orderRepository, times(1)).save(any(Order.class));
	}

    @Test
	public void Create_ValidParameters_ShoudBeCorrectReturn() throws Exception {

		// given
        Order order = this.fixture.create(Order.class);

		when(orderRepository.save(any(Order.class))).thenReturn(true);

		// when
		OrderResponse orderResponse =  orderService.create(order);

		// then
		assertNotNull(orderResponse);
        assertNotNull(orderResponse.getId());
	}

    @Test(expected = Exception.class)
	public void Create_ValidParameters_ShoudBeReturnException() throws Exception {

		// given
        Order order = this.fixture.create(Order.class);
        
		when(orderRepository.save(any(Order.class))).thenThrow(Exception.class);

		// when
		orderService.create(order);
	}

     // Update
    @Test
    public void Update_ValidParameters_OrderRepositoryCalled() throws Exception {

		// given
        Order order = this.fixture.create(Order.class);

        when(orderRepository.findById(any(Long.class))).thenReturn(order);    

		when(orderRepository.update(any(Order.class))).thenReturn(true);

		// when
		orderService.update(order);

		// then
		verify(orderRepository, times(1)).update(any(Order.class));
        verify(orderRepository, times(1)).findById(any(Long.class));
	}

    @Test
	public void Update_ValidParameters_ShoudBeCorrectReturn() throws Exception {

		// given
        Order order = this.fixture.create(Order.class);
        
        when(orderRepository.findById(order.getId())).thenReturn(order);

		when(orderRepository.update(any(Order.class))).thenReturn(true);

		// when
		OrderResponse orderResponse =  orderService.update(order);

		// then
		assertNotNull(orderResponse);
        assertNotNull(orderResponse.getId());

	}

    @Test(expected = EntityNotFoundException.class)
	public void Update_ValidParameters_ShoudBeReturnEntityNotFoundException() throws Exception {

		// given
        Order order = this.fixture.create(Order.class);

        when(orderRepository.findById(order.getId())).thenThrow(EntityNotFoundException.class);    

		when(orderRepository.update(any(Order.class))).thenReturn(true);
        
        // when
		orderService.update(order);
	}

    @Test(expected = Exception.class)
	public void Update_ValidParameters_ShoudBeReturnException() throws Exception {

		// given
        Order order = this.fixture.create(Order.class);

        when(orderRepository.findById(order.getId())).thenReturn(order);
        
		when(orderRepository.update(any(Order.class))).thenThrow(Exception.class);

		// when
		orderService.update(order);
	}
 
 
 
}
