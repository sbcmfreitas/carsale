package io.carsale.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * ModelApiResponse
 */
@Validated
public class ModelApiResponse   {
  
    @JsonProperty("code")
    private Integer code = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("message")
    private String message = null;

    public ModelApiResponse code(Integer code) {
      this.code = code;
      return this;
    }

    /**
     * Get code
     * @return code
     **/
    @Schema(description = "")
      public Integer getCode() {
      return code;
    }

    public void setCode(Integer code) {
      this.code = code;
    }

  

    /**
     * Get type
     * @return type
     **/
    @Schema(description = "")
      public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }


    /**
     * Get message
     * @return message
     **/
    @Schema(description = "")
      public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }


    @Override
    public boolean equals(java.lang.Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ModelApiResponse _apiResponse = (ModelApiResponse) o;
      return Objects.equals(this.code, _apiResponse.code) &&
          Objects.equals(this.type, _apiResponse.type) &&
          Objects.equals(this.message, _apiResponse.message);
    }

    @Override
    public int hashCode() {
      return Objects.hash(code, type, message);
    }

 
}
