package io.carsale.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.validation.annotation.Validated;

import io.carsale.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Order Request
 */
@Validated
public class OrderRequest {

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("CarId")
    private Long carId = null;

    @JsonProperty("ClientId")
    private Long clientId = null;

    @JsonProperty("value")
    private BigDecimal value = null;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	  @DateTimeFormat(iso = ISO.DATE_TIME)
    @JsonProperty("orderDate")
    private Date orderDate = null;
  
    @JsonProperty("status")
    private StatusEnum status = null;

   /**
     * Get id
     * 
     * @return id
     **/
    @Schema(required = true, description = "") @Valid
    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    /**
     * Get carId
     * 
     * @return carId
     **/
    @Schema(required = true, description = "") @NotNull @Valid
    public Long getCarId() {
      return carId;
    }

    public void setCarId(Long carId) {
      this.carId = carId;
    }

    /**
     * Get clientId
     * 
     * @return clientId
     **/
    @Schema(required = true, description = "") @NotNull @Valid
    public Long getClientId() {
      return clientId;
    }

    public void setClientId(Long clientId) {
      this.clientId = clientId;
    }

    /**
     * Get value
     * 
     * @return value
     **/
    @Schema(required = true, description = "") @NotNull @Valid
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
      @Schema(required = true, description = "") @NotNull @Valid
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
    @Schema(required = true, description = "Order Status") @NotNull
      public StatusEnum getStatus() {
      return status;
    }

    public void setStatus(StatusEnum status) {
      this.status = status;
    }

}
