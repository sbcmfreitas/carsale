package io.carsale.adapter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotNull;

import io.carsale.dto.CarResponse;
import io.carsale.dto.ClientResponse;
import io.carsale.dto.OrderResponse;
import io.carsale.model.Order;
import io.swagger.v3.oas.annotations.media.Schema;

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
  @Schema(required = true, description = "") @NotNull
  public Long getId() {
    return order.getId();
  }

 /**
   * Get client
   * @return client
   **/
  @Schema(required = true, description = "") @NotNull
  public ClientResponse getClient() {
    return new ClientResponseAdapter(order.getClient());
  }


  /**
   * Get car
   * @return car
   **/
  @Schema(required = true, description = "") @NotNull
    public CarResponse getCar() {
    return new CarResponseAdapter(order.getCar());
  }


  /**
   * Get value
   * @return value
   **/
  @Schema(required = true, description = "") @NotNull 
    public BigDecimal getValue() {
    return order.getValue();
  }


  /**
   * Get orderDate
   * @return orderDate
   **/
  @Schema(required = true, description = "") @NotNull
    public String getOrderDate() {
    return dformat.format(order.getOrderDate());
  }


  /**
   * Order Status
   * @return status
   **/
  @Schema(required = true, description = "Order Status") @NotNull
    public String getStatus() {
    return order.getStatus().toString();
  }

}
