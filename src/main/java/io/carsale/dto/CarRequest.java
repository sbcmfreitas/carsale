package io.carsale.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Car Request
 */
@Validated
public class CarRequest{

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("brandId")
    private Long brandId = null;

    @JsonProperty("value")   
    private BigDecimal value = null;

    @JsonProperty("optionIds")
    private List<Long> optionIds;

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
     * Get brandId
     * 
     * @return brandId
     **/
    @Schema(required = true, description = "") @NotNull @Valid
    public Long getBrandId() {
      return brandId;
    }

    public void setBrandId(Long brandId) {
      this.brandId = brandId;
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
     * Get option ids
     * 
     * @return option ids
     **/
    @Schema(required = true, description = "") @NotNull @Valid
    public List<Long> getOptionIds() {
      return optionIds;
    }

    public void setOptionIds(List<Long> optionIds) {
      this.optionIds = optionIds;
    }



}
