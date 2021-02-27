package io.carsale.repository.interfaces;

import java.util.List;

import io.carsale.model.Client;

public interface ClientRespository {

    boolean save(Client client);

    boolean update(Client client);

    boolean delete(Long id);

    Client findById(Long id);

    List<Client> findAll();
}
