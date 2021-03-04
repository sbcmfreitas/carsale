package io.carsale.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.carsale.adapter.ModelMapperFactory;
import io.carsale.dto.OrderResponse;
import io.carsale.model.Order;
import io.carsale.repository.interfaces.OrderRespository;
import io.carsale.service.interfaces.OrderService;

@Service
@Transactional(readOnly = false)
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRespository orderRepository;

	private ModelMapper modelMapper = ModelMapperFactory.getInstance();

	@Override
	public OrderResponse create(Order order) {

		orderRepository.save(order);

		return modelMapper.map(order, OrderResponse.class);
	}

	@Override
	public OrderResponse update(Order order) {

		if(this.find(order.getId())!=null){
			orderRepository.update(order);
		}

		return modelMapper.map(order, OrderResponse.class);
	}

	@Override
	public boolean delete(Long id) {
		orderRepository.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public OrderResponse find(Long id) {

		Order order = this.orderRepository.findById(id);

		if(order==null){
			throw new EntityNotFoundException();
		}

		return modelMapper.map(order, OrderResponse.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderResponse> findAll() {
		return orderRepository.findAll().stream().map(p-> modelMapper.map(p, OrderResponse.class)).collect(Collectors.toList());
	}

}
