package io.carsale.adapter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import io.carsale.dto.CarResponse;
import io.carsale.dto.ClientResponse;
import io.carsale.dto.OrderResponse;
import io.carsale.model.Order;

public class OrderResponseAdapter extends OrderResponse {

  private Order order = null;

  private SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

  public OrderResponseAdapter(Order order) {
     super();
     this.order = order;
  }

/**
  * Get id
  * @return id
  **/
  public Long getId() {
    return order.getId();
  }

 /**
   * Get client
   * @return client
   **/
  public ClientResponse getClient() {
    return new ClientResponseAdapter(order.getClient());
  }


  /**
   * Get car
   * @return car
   **/
  public CarResponse getCar() {
    return new CarResponseAdapter(order.getCar());
  }


  /**
   * Get value
   * @return value
   **/
  public BigDecimal getValue() {
    return order.getValue();
  }


  /**
   * Get orderDate
   * @return orderDate
   **/
  public String getOrderDate() {
    return dformat.format(order.getOrderDate());
  }


  /**
   * Order Status
   * @return status
   **/
  public String getStatus() {
    return order.getStatus().toString();
  }

}
