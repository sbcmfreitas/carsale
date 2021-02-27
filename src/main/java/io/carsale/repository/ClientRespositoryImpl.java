package io.carsale.repository;

import org.springframework.stereotype.Repository;

import io.carsale.model.Client;
import io.carsale.repository.interfaces.ClientRespository;

@Repository
public class ClientRespositoryImpl extends AbstractRepository<Client, Long> implements ClientRespository{
}
