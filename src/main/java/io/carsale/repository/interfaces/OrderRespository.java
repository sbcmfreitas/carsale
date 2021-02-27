package io.carsale.repository.interfaces;

import java.util.List;

import io.carsale.model.Order;

public interface OrderRespository {

    boolean save(Order order);

    boolean update(Order order);

    boolean delete(Long id);

    Order findById(Long id);

    List<Order> findAll();
}
