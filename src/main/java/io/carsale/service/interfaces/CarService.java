package io.carsale.service.interfaces;

import java.util.List;

import io.carsale.dto.CarResponse;
import io.carsale.model.Car;

public interface CarService {

    CarResponse create(Car car);

	CarResponse update(Car car);

	boolean delete(Long id);

	CarResponse find(Long id);

    List<CarResponse> findAll();  
}
