package io.carsale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.carsale.model.Option;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Option Response (DTO)
 */
public class OptionResponse{

    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;


   /**
     * Constructor
     * 
     **/
    public OptionResponse(Option option) {
      this.id = option.getId();
      this.name = option.getName();
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
