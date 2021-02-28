package io.carsale.dto;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * Order Response (DTO)
 */
@Validated
public class OrderResponse {


  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("car")
  private CarResponse car = null;

  @JsonProperty("client")
  private ClientResponse client = null;

  @JsonProperty("value")
  private BigDecimal value = null;

  @JsonProperty("orderDate")
  private String orderDate = null;

  @JsonProperty("status")
  private String status = null;

  /**
  * Get id
  * 
  * @return id
  **/
  @Schema(required = true, description = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  /**
   * Get car
   * @return car
   **/
  @Schema(required = true, description = "")
  public CarResponse getCar() {
    return car;
  }

  public void setCar(CarResponse car) {
    this.car = car;
  }

    /**
   * Get client
   * @return client
   **/
  @Schema(required = true, description = "")
  public ClientResponse getClient() {
    return client;
  }

  public void setClient(ClientResponse client) {
    this.client = client;
  }

    /**
   * Get value
   * @return value
   **/
  @Schema(required = true, description = "")
  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

    /**
   * Get Order Date
   * @return Order Date
   **/
  @Schema(required = true, description = "")
  public String getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }

    /**
   * Get status
   * @return status
   **/
  @Schema(required = true, description = "")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
