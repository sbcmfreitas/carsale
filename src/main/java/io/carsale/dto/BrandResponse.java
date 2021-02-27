package io.carsale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.carsale.model.Brand;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Brand Response (DTO)
 */
public class BrandResponse{

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

   /**
     * Constructor
     **/
    public BrandResponse(Brand brand) {
      this.id = brand.getId()!=null?brand.getId():null;
      this.name = brand.getName()!=null?brand.getName():null;
    }

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
     * Get name
     * 
     * @return name
     **/
    @Schema(description = "")
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }    

}
