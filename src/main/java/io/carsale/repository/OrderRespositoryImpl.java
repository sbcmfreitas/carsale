package io.carsale.repository;

import org.springframework.stereotype.Repository;

import io.carsale.model.Order;
import io.carsale.repository.interfaces.OrderRespository;

@Repository
public class OrderRespositoryImpl extends AbstractRepository<Order, Long> implements OrderRespository{
}
