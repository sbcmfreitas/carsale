package io.carsale.dto;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonProperty;

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
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinColumn(name = "car_id", nullable = false)
  private CarResponse car = null;

  @JsonProperty("client")
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinColumn(name = "client_id", nullable = false)
  private ClientResponse client = null;

  @JsonProperty("value")
  @Digits(integer=9, fraction=2)   
  @Column(name = "value", nullable= false, precision=7, scale=2)  
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
  
  /**
   * Get car
   * @return car
   **/
  @Schema(required = true, description = "")
  public CarResponse getCar() {
    return car;
  }

    /**
   * Get client
   * @return client
   **/
  @Schema(required = true, description = "")
  public ClientResponse getClient() {
    return client;
  }

    /**
   * Get value
   * @return value
   **/
  @Schema(required = true, description = "")
  public BigDecimal getValue() {
    return value;
  }

    /**
   * Get Order Date
   * @return Order Date
   **/
  @Schema(required = true, description = "")
  public String getOrderDate() {
    return orderDate;
  }

    /**
   * Get status
   * @return status
   **/
  @Schema(required = true, description = "")
  public String getStatus() {
    return status;
  }

 


  


}
