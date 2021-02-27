package io.carsale.adapter;

import io.carsale.dto.ClientResponse;
import io.carsale.model.Client;

public class ClientResponseAdapter extends ClientResponse {

  private Client client = null;

  public ClientResponseAdapter(Client client) {
     super();
     this.client = client;
  }

   
  /**
   * Get Id
   * @return Id
   **/
  @Override
  public Long getId() {
    return client.getId();
  }

  /**
   * Get Name
   * @return Name
   **/
  @Override
  public String getName() {
    return String.format("%s %s",this.client.getFirstName(),this.client.getLastName());
  }

  /**
   * Get email
   * @return email
   **/
  @Override
  public String getEmail() {
    return client.getEmail();
  }

  /**
   * Get phone
   * @return phone
   **/
  @Override
  public String getPhone() {
    return client.getPhone();
  }


}
