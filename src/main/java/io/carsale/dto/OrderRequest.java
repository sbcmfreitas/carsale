package io.carsale.dto;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.OffsetTime;

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

    @JsonProperty("orderDate")
    private OffsetDateTime orderDate = null;
  
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
      public OffsetDateTime getOrderDate() {
      return orderDate;
    }

    public void setOrderDate(OffsetDateTime orderDate) {
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
