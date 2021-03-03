package com.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.flextrade.jfixture.JFixture;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

import io.carsale.dto.ClientResponse;
import io.carsale.model.Client;
import io.carsale.repository.interfaces.ClientRespository;
import io.carsale.service.ClientServiceImpl;

@SpringBootTest
public class ClientServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    ClientServiceImpl clientService;

    @Mock
    ClientRespository clientRepository;

    JFixture fixture;

    @Before
    public void setUp() {

        this.fixture = new JFixture();
        this.fixture.customise().circularDependencyBehaviour().omitSpecimen(); // omit circular dependencies
        this.fixture.customise().noResolutionBehaviour().omitSpecimen(); // omit methods that cannot be resolved

    }

    // FindAll
    @Test
    public void FindAll_ValidParameters_ClientRepositoryCalled() {

        // given
        List<Client> clientList = Arrays.asList(this.fixture.create(Client.class));

        when(clientRepository.findAll()).thenReturn(clientList);

        // when
        clientService.findAll();

        // then
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void FindAll_ValidParameters_ShoudBeCorrectReturn() {

        // given
        List<Client> clientList = Arrays.asList(this.fixture.create(Client.class));

        when(clientRepository.findAll()).thenReturn(clientList);

        // when
        List<ClientResponse> clientResponseList = clientService.findAll();

        // then
        assertFalse(clientResponseList.isEmpty());

    }

    @Test(expected = Exception.class)
    public void FindAll_ValidParameters_ShoudBeReturnException() {

        // given
        when(clientRepository.findAll()).thenThrow(new Exception());

        // when
        clientService.findAll();

    }

    // Create
    @Test
    public void Create_ValidParameters_ClientRepositoryCalled() throws Exception {

		// given
        Client client = this.fixture.create(Client.class);

		when(clientRepository.save(any(Client.class))).thenReturn(true);

		// when
		clientService.create(client);

		// then
		verify(clientRepository, times(1)).save(any(Client.class));
	}

    @Test
	public void Create_ValidParameters_ShoudBeCorrectReturn() throws Exception {

		// given
        Client client = this.fixture.create(Client.class);

		when(clientRepository.save(any(Client.class))).thenReturn(true);

		// when
		ClientResponse clientResponse =  clientService.create(client);

		// then
		assertNotNull(clientResponse);
        assertNotNull(clientResponse.getId());
	}

    @Test(expected = Exception.class)
	public void Create_ValidParameters_ShoudBeReturnException() throws Exception {

		// given
        Client client = this.fixture.create(Client.class);
        
		when(clientRepository.save(any(Client.class))).thenThrow(Exception.class);

		// when
		clientService.create(client);
	}

     // Update
    @Test
    public void Update_ValidParameters_ClientRepositoryCalled() throws Exception {

		// given
        Client client = this.fixture.create(Client.class);

        when(clientRepository.findById(any(Long.class))).thenReturn(client);    

		when(clientRepository.update(any(Client.class))).thenReturn(true);

		// when
		clientService.update(client);

		// then
		verify(clientRepository, times(1)).update(any(Client.class));
        verify(clientRepository, times(1)).findById(any(Long.class));
	}

    @Test
	public void Update_ValidParameters_ShoudBeCorrectReturn() throws Exception {

		// given
        Client client = this.fixture.create(Client.class);
        
        when(clientRepository.findById(client.getId())).thenReturn(client);

		when(clientRepository.update(any(Client.class))).thenReturn(true);

		// when
		ClientResponse clientResponse =  clientService.update(client);

		// then
		assertNotNull(clientResponse);
        assertNotNull(clientResponse.getId());

	}

    @Test(expected = EntityNotFoundException.class)
	public void Update_ValidParameters_ShoudBeReturnEntityNotFoundException() throws Exception {

		// given
        Client client = this.fixture.create(Client.class);

        when(clientRepository.findById(client.getId())).thenThrow(EntityNotFoundException.class);    

		when(clientRepository.update(any(Client.class))).thenReturn(true);
        
        // when
		clientService.update(client);
	}

    @Test(expected = Exception.class)
	public void Update_ValidParameters_ShoudBeReturnException() throws Exception {

		// given
        Client client = this.fixture.create(Client.class);

        when(clientRepository.findById(client.getId())).thenReturn(client);
        
		when(clientRepository.update(any(Client.class))).thenThrow(Exception.class);

		// when
		clientService.update(client);
	}
 
 
 
}
