package io.carsale.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.carsale.adapter.OrderResponseAdapter;
import io.carsale.dto.OrderResponse;
import io.carsale.model.Order;
import io.carsale.repository.interfaces.OrderRespository;
import io.carsale.service.interfaces.OrderService;

@Service
@Transactional(readOnly = false)
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRespository orderRepository;

	@Override
	public OrderResponse create(Order order) {

		orderRepository.save(order);

		return new OrderResponseAdapter(order);
	}

	@Override
	public OrderResponse update(Order order) {

		OrderResponse orderResponse = this.find(order.getId());

		if(orderResponse.getId()==null){
			throw new EntityNotFoundException();
		}

		orderRepository.update(order);

		return this.find(order.getId());
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

		return new OrderResponseAdapter(order);
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrderResponse> findAll() {
		return orderRepository.findAll().stream().map(p-> new OrderResponseAdapter(p)).collect(Collectors.toList());
	}

}
