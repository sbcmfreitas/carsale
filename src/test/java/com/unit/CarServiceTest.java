package com.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

import io.carsale.dto.CarResponse;
import io.carsale.exception.OptionQuantityRestrictionException;
import io.carsale.model.Car;
import io.carsale.model.Option;
import io.carsale.repository.interfaces.CarRespository;
import io.carsale.service.CarServiceImpl;

@SpringBootTest
public class CarServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    CarServiceImpl carService;

    @Mock
    CarRespository carRepository;

    JFixture fixture;

    @Before
    public void setUp() {

        this.fixture = new JFixture();
        this.fixture.customise().circularDependencyBehaviour().omitSpecimen(); // omit circular dependencies
        this.fixture.customise().noResolutionBehaviour().omitSpecimen(); // omit methods that cannot be resolved

    }

    // FindAll
    @Test
    public void FindAll_ValidParameters_CarRepositoryCalled() {

        // given
        List<Car> carList = Arrays.asList(this.fixture.create(Car.class));

        when(carRepository.findAll()).thenReturn(carList);

        // when
        carService.findAll();

        // then
        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void FindAll_ValidParameters_ShoudBeCorrectReturn() {

        // given
        List<Car> carList = Arrays.asList(this.fixture.create(Car.class));

        when(carRepository.findAll()).thenReturn(carList);

        // when
        List<CarResponse> carResponseList = carService.findAll();

        // then
        assertFalse(carResponseList.isEmpty());

    }

    @Test(expected = Exception.class)
    public void FindAll_ValidParameters_ShoudBeReturnException() {

        // given
        when(carRepository.findAll()).thenThrow(new Exception());

        // when
        carService.findAll();

    }

    // Create
    @Test
    public void Create_ValidParameters_CarRepositoryCalled() throws Exception {

		// given
        Car car = this.fixture.create(Car.class);
            car.getOptions().clear();
            car.getOptions().add((Option)this.fixture.create(Option.class));

		when(carRepository.save(any(Car.class))).thenReturn(true);

		// when
		carService.create(car);

		// then
		verify(carRepository, times(1)).save(any(Car.class));
	}

    @Test
	public void Create_ValidParameters_ShoudBeCorrectReturn() throws Exception {

		// given
        Car car = this.fixture.create(Car.class);
            car.getOptions().clear();
            car.getOptions().add((Option)this.fixture.create(Option.class));

		when(carRepository.save(any(Car.class))).thenReturn(true);

		// when
		CarResponse carResponse =  carService.create(car);

		// then
		assertNotNull(carResponse);
        assertNotNull(carResponse.getId());
        assertNotNull(carResponse.getBrand());
        assertNotNull(carResponse.getOptions());
        assertTrue(carResponse.getOptions().size()==1);
        assertNotNull(carResponse.getValue());

	}

    @Test(expected = OptionQuantityRestrictionException.class)
	public void Create_ValidParameters_ShoudBeReturnOptionQuantityRestrictionException() throws Exception {

		// given
        Car car = this.fixture.create(Car.class);

		when(carRepository.save(any(Car.class))).thenReturn(true);

        // when
		carService.create(car);
	}

    @Test(expected = Exception.class)
	public void Create_ValidParameters_ShoudBeReturnException() throws Exception {

		// given
        Car car = this.fixture.create(Car.class);
        
		when(carRepository.save(any(Car.class))).thenThrow(Exception.class);

		// when
		carService.create(car);
	}

     // Update
    @Test
    public void Update_ValidParameters_CarRepositoryCalled() throws Exception {

		// given
        Car car = this.fixture.create(Car.class);
            car.getOptions().clear();
            car.getOptions().add((Option)this.fixture.create(Option.class));

        when(carRepository.findById(car.getId())).thenReturn(car);    

		when(carRepository.update(any(Car.class))).thenReturn(true);

		// when
		carService.update(car);

		// then
		verify(carRepository, times(1)).update(any(Car.class));
        verify(carRepository, times(1)).findById(any(Long.class));
	}

    @Test
	public void Update_ValidParameters_ShoudBeCorrectReturn() throws Exception {

		// given
        Car car = this.fixture.create(Car.class);
            car.getOptions().clear();
            car.getOptions().add((Option)this.fixture.create(Option.class));
        
        when(carRepository.findById(car.getId())).thenReturn(car);

		when(carRepository.update(any(Car.class))).thenReturn(true);

		// when
		CarResponse carResponse =  carService.update(car);

		// then
		assertNotNull(carResponse);
        assertNotNull(carResponse.getId());
        assertNotNull(carResponse.getBrand());
        assertNotNull(carResponse.getOptions());
        assertTrue(carResponse.getOptions().size()==1);
        assertNotNull(carResponse.getValue());

	}

    @Test(expected = OptionQuantityRestrictionException.class)
	public void Update_ValidParameters_ShoudBeReturnOptionQuantityRestrictionException() throws Exception {

		// given
        Car car = this.fixture.create(Car.class);

		when(carRepository.update(any(Car.class))).thenReturn(true);
        
        // when
		carService.update(car);
	}

    @Test(expected = EntityNotFoundException.class)
	public void Update_ValidParameters_ShoudBeReturnEntityNotFoundException() throws Exception {

		// given
        Car car = this.fixture.create(Car.class);
            car.getOptions().clear();
            car.getOptions().add((Option)this.fixture.create(Option.class));

        when(carRepository.findById(car.getId())).thenThrow(EntityNotFoundException.class);    

		when(carRepository.update(any(Car.class))).thenReturn(true);
        
        // when
		carService.update(car);
	}

    @Test(expected = Exception.class)
	public void Update_ValidParameters_ShoudBeReturnException() throws Exception {

		// given
        Car car = this.fixture.create(Car.class);

        when(carRepository.findById(car.getId())).thenReturn(car);
        
		when(carRepository.update(any(Car.class))).thenThrow(Exception.class);

		// when
		carService.update(car);
	}
 
 
 
}
