package io.carsale.repository;

import org.springframework.stereotype.Repository;

import io.carsale.model.Car;
import io.carsale.repository.interfaces.CarRespository;


@Repository
public class CarRespositoryImpl extends AbstractRepository<Car, Long> implements CarRespository{
}
