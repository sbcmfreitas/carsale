package io.carsale.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.carsale.adapter.ModelMapperFactory;
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

	private ModelMapper modelMapper = ModelMapperFactory.getInstance();

	@Override
	public CarResponse create(Car car) throws OptionQuantityRestrictionException {

		if(car.getOptions().size()>2){
			throw new OptionQuantityRestrictionException(1,LIMIT_MESSAGE);
		}
		
		carRepository.save(car);

		return modelMapper.map(car, CarResponse.class);
	}

	@Override
	public CarResponse update(Car car) throws OptionQuantityRestrictionException{

		if(car.getOptions().size()>2){
			throw new OptionQuantityRestrictionException(1,LIMIT_MESSAGE);
		}

		if( this.find(car.getId()) != null ){
			carRepository.update(car);
		}

		return modelMapper.map(car, CarResponse.class);
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

		return modelMapper.map(car, CarResponse.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CarResponse> findAll() {
		return carRepository.findAll().stream().map(p-> modelMapper.map(p, CarResponse.class)).collect(Collectors.toList());
	}

}
