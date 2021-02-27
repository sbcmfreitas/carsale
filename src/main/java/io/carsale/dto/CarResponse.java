package io.carsale.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Car Response Model 
 */
@Validated
public class CarResponse{

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("brand")
    private BrandResponse brand = null;

    @JsonProperty("value")   
    private BigDecimal value = null;

    @JsonProperty("options")
    private List<OptionResponse> options;


   /**
     * Get id
     * 
     * @return id
     **/
    @Schema(required = true, description = "") @NotNull @Valid
    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    /**
     * Get Brand
     * 
     * @return Brand
     **/
    @Schema(description = "")
    public BrandResponse getBrand() {
      return brand;
    }

    public void setBrand(BrandResponse brand) {
      this.brand = brand;
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
     * Get List of options 
     * 
     * @return List of options
     **/
    @Schema(required = true, description = "")
    public List<OptionResponse> getOptions() {
      return options;
    }

    public void setOptions(List<OptionResponse> options) {
      this.options = options;
    }

}
