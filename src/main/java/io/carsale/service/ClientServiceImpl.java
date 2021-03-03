package io.carsale.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.carsale.adapter.ClientResponseAdapter;
import io.carsale.dto.ClientResponse;
import io.carsale.model.Client;
import io.carsale.repository.interfaces.ClientRespository;
import io.carsale.service.interfaces.ClientService;

@Service
@Transactional(readOnly = false)
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRespository clientRepository;

	@Override
	public ClientResponse create(Client client) {

		clientRepository.save(client);

		return new ClientResponseAdapter(client);
	}

	@Override
	public ClientResponse update(Client client) {

		if( this.find(client.getId()) != null ){
			clientRepository.update(client);
		}

		return new ClientResponseAdapter(client);
	}

	@Override
	public boolean delete(Long id) {
		clientRepository.delete(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public ClientResponse find(Long id) {

		Client client = this.clientRepository.findById(id);

		if(client==null){
			throw new EntityNotFoundException();
		}

		return new ClientResponseAdapter(client);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientResponse> findAll() {
		return clientRepository.findAll().stream().map(p-> new ClientResponseAdapter(p)).collect(Collectors.toList());
	}

}
