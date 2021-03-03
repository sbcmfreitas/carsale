package io.carsale.api;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.carsale.api.interfaces.ClientApi;
import io.carsale.dto.ClientRequest;
import io.carsale.dto.ClientResponse;
import io.carsale.model.Client;
import io.carsale.service.interfaces.ClientService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
public class ClientApiController implements ClientApi {

    @Autowired
    private ClientService clientService;

    public ResponseEntity<ClientResponse> createClient(
        @Parameter(in = ParameterIn.DEFAULT, description = "Created client object", required=true, schema=@Schema()) 
        @Valid @RequestBody ClientRequest clientRequest) {

        try {

            ClientResponse clientResponse = this.clientService.create(new Client(clientRequest));

            return new ResponseEntity<ClientResponse>(clientResponse, HttpStatus.CREATED);

        }catch (JpaObjectRetrievalFailureException|DataIntegrityViolationException e) {

            return new ResponseEntity<ClientResponse>(HttpStatus.BAD_REQUEST);

        }catch (Exception e) {

            throw e;

        }
    }

    public ResponseEntity<ClientResponse> updateClient(
        @Parameter(in = ParameterIn.DEFAULT, description = "Updated client object", required=true, schema=@Schema())
        @Valid @RequestBody ClientRequest clientRequest) {
        
        try {

            ClientResponse carResponse = this.clientService.update(new Client(clientRequest));

            return new ResponseEntity<ClientResponse>(carResponse, HttpStatus.ACCEPTED);

        }catch (EntityNotFoundException e) {

            return new ResponseEntity<ClientResponse>(HttpStatus.NOT_FOUND);
            
        }catch (JpaObjectRetrievalFailureException|DataIntegrityViolationException e) {

            return new ResponseEntity<ClientResponse>(HttpStatus.BAD_REQUEST);

        }catch (Exception e) {

            throw e;

        }

    }

    public ResponseEntity<ClientResponse> getClientByid(
        @Parameter(in = ParameterIn.PATH, description = "Get client by id", required=true, schema=@Schema())
        @PathVariable("id") Long id) {
       
        try {

            ClientResponse clientResponse = this.clientService.find(id);

            return new ResponseEntity<ClientResponse>(clientResponse,HttpStatus.OK);

        }catch (JpaObjectRetrievalFailureException|EntityNotFoundException e) {

            return new ResponseEntity<ClientResponse>(HttpStatus.NOT_FOUND);

        }catch (Exception e) {

            throw e;

        }
    }

    public ResponseEntity<List<ClientResponse>> getClients() {

        try {

            List<ClientResponse> clients = this.clientService.findAll();
            return new ResponseEntity<List<ClientResponse>>(clients, HttpStatus.OK);
                
        }catch (Exception e) {

            throw e;

        }
    }

    public ResponseEntity<Void> deleteClient(
        @Parameter(in = ParameterIn.PATH, description = "The id of object to delete", required=true, schema=@Schema()) 
        @PathVariable("id") Long id) {

        try {

            if(this.clientService.delete(id)){
                
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);          
            }

            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (JpaObjectRetrievalFailureException|EntityNotFoundException e) {

            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

        }catch (Exception e) {

            throw e;

        }

    }

}
