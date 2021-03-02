package io.carsale.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Client Response Model (DTO) 
 */
@Validated
public class ClientResponse {

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("Name")
  private String name = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("phone")
  private String phone = null;


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
   * Get lastName
   * @return lastName
   **/
  @Schema(required = true, description = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  /**
   * Get email
   * @return email
   **/
  @Schema(required = true, description = "")
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  /**
   * Get phone
   * @return phone
   **/
  @Schema(required = true, description = "")
    public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


}
