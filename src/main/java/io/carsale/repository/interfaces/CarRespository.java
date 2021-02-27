package io.carsale.repository.interfaces;

import java.util.List;

import io.carsale.model.Car;

public interface CarRespository {

    boolean save(Car car);

    boolean update(Car car);

    boolean delete(Long id);

    Car findById(Long id);

    List<Car> findAll();
}
