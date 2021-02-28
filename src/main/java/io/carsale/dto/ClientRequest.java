package io.carsale.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Order Request
 */
@Validated
public class ClientRequest {


  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("phone")
  private String phone = null;


 /**
   * Get Id
   * @return id
   **/
  @Schema(required = true, description = "") @Valid
  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(required = true, description = "") @NotNull @Valid
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(required = true, description = "") @NotNull @Valid
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  /**
   * Get email
   * @return email
   **/
  @Schema(required = true, description = "") @NotNull @Valid
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
  @Schema(required = true, description = "") @NotNull @Valid
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

}
