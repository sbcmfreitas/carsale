package io.carsale.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.carsale.adapter.ModelMapperFactory;
import io.carsale.dto.ClientResponse;
import io.carsale.model.Client;
import io.carsale.repository.interfaces.ClientRespository;
import io.carsale.service.interfaces.ClientService;

@Service
@Transactional(readOnly = false)
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRespository clientRepository;

	private ModelMapper modelMapper = ModelMapperFactory.getInstance();

	@Override
	public ClientResponse create(Client client) {

		clientRepository.save(client);

		return modelMapper.map(client, ClientResponse.class);
	}

	@Override
	public ClientResponse update(Client client) {

		if( this.find(client.getId()) != null ){
			clientRepository.update(client);
		}

		return modelMapper.map(client, ClientResponse.class);
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

		return modelMapper.map(client, ClientResponse.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientResponse> findAll() {
		return clientRepository.findAll().stream().map(p-> modelMapper.map(p, ClientResponse.class)).collect(Collectors.toList());
	}

}
