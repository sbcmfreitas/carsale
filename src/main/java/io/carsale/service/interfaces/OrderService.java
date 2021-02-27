package io.carsale.service.interfaces;

import java.util.List;

import io.carsale.dto.OrderResponse;
import io.carsale.model.Order;

public interface OrderService {

    OrderResponse create(Order order);

	OrderResponse update(Order order);

	boolean delete(Long id);

	OrderResponse find(Long id);

    List<OrderResponse> findAll();  
}
