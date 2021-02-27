package io.carsale.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.*;

/**
 * Client
 */
@Entity
@SuppressWarnings("serial")
public class Client extends AbstractEntity<Long> {
  

  @JsonProperty("firstName")
  @Column(name="firstname", nullable = false, length = 50)
  private String firstName = null;

  @JsonProperty("lastName")
  @Column(name="lastname", nullable = false, length = 50)
  private String lastName = null;

  @JsonProperty("email")
  @Email(message = "Invalid mail")
  @Column(name="email", nullable = false, unique = true, length = 200)
  private String email = null;

  @JsonProperty("phone")
  @Column(name="phone", nullable = false, length = 15)
  private String phone = null;


 /**
   * Constructor
   **/
  public Client() {
    super();
  }

  public Client(Long id) {
    super();
    this.id = id;
  }

  /**
   * Get firstName
   * @return firstName
   **/
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
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


}
