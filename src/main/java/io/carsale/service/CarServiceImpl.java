package io.carsale.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import io.carsale.adapter.CarResponseAdapter;
import io.carsale.dto.CarResponse;
import io.carsale.exception.OptionQuantityRestrictionException;
import io.carsale.model.Car;
import io.carsale.repository.interfaces.CarRespository;
import io.carsale.service.interfaces.CarService;


@Service
@Transactional(readOnly = false)
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRespository carRepository;

	private static final String LIMIT_MESSAGE = "Limit of 2 options has been exceeded";

	@Override
	public CarResponse create(Car car) throws OptionQuantityRestrictionException {

		if(car.getOptions().size()>2){
			throw new OptionQuantityRestrictionException(1,LIMIT_MESSAGE);
		}

		carRepository.save(car);

		return new CarResponseAdapter(car);
	}

	@Override
	public CarResponse update(Car car) throws OptionQuantityRestrictionException{

		if(car.getOptions().size()>2){
			throw new OptionQuantityRestrictionException(1,LIMIT_MESSAGE);
		}

		CarResponse carResponse = this.find(car.getId());

		if(carResponse.getId()==null){
			throw new EntityNotFoundException();
		}

		carRepository.update(car);

		return this.find(car.getId());
	}

	@Override
	public boolean delete(Long id) {
		carRepository.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public CarResponse find(Long id) {

		Car car = this.carRepository.findById(id);

		if(car==null){
			throw new EntityNotFoundException();
		}

		return new CarResponseAdapter(car);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CarResponse> findAll() {
		return carRepository.findAll().stream().map(p-> new CarResponseAdapter(p)).collect(Collectors.toList());
	}

}
