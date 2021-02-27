package io.carsale.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import io.carsale.dto.OrderRequest;
import io.carsale.enums.StatusEnum;


/**
 * Order
 */
@Entity
@Validated
@Table(name="car_order")
@SuppressWarnings("serial")
public class Order extends AbstractEntity<Long>{


  @ManyToOne
  @JoinColumn(name = "car_id", nullable = false)
  private Car car = null;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = false)
  private Client client = null;

  @Digits(integer=9, fraction=2)   
  @Column(name = "value", nullable= false, precision=7, scale=2)  
  private BigDecimal value = null;

	@Temporal(TemporalType.TIMESTAMP)
  @Column(name = "order_date", nullable = false)  
  private Date orderDate = null;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private StatusEnum status = null;

  public Order(){
    super();
  }
  
  public Order(OrderRequest orderRequest) {
    this.id = orderRequest.getId()!=null ? orderRequest.getId():null;   
    this.car = new Car(orderRequest.getCarId());
    this.client = new Client(orderRequest.getClientId());
    this.value = orderRequest.getValue();
    this.orderDate = orderRequest.getOrderDate();
    this.status = orderRequest.getStatus();
  }

  /**
   * Get client
   * @return client
   **/
  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }


  /**
   * Get car
   * @return car
   **/
  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }


  /**
   * Get value
   * @return value
   **/
  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }


  /**
   * Get orderDate
   * @return orderDate
   **/
  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }




  /**
   * Order Status
   * @return status
   **/
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }




}
