package io.carsale.service.interfaces;

import java.util.List;

import io.carsale.dto.ClientResponse;
import io.carsale.model.Client;

public interface ClientService {

    ClientResponse create(Client client);

	ClientResponse update(Client client);

	boolean delete(Long id);

	ClientResponse find(Long id);

    List<ClientResponse> findAll();  
}
