package io.carsale.api.interfaces;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.carsale.dto.ClientRequest;
import io.carsale.dto.ClientResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ClientApi {

    @Operation(summary = "Create client", description = "Create the client.", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ClientResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/client",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<ClientResponse> createClient(
        @Parameter(in = ParameterIn.DEFAULT, description = "Created client object", required=true, schema=@Schema()) 
        @Valid @RequestBody ClientRequest clientRequest);



    @Operation(summary = "Updated client", description = "Updated client.", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "202", description = "Accepted", content = @Content(schema = @Schema(implementation = ClientResponse.class))),  
        @ApiResponse(responseCode = "400", description = "Bad request"), 
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/client",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<ClientResponse> updateClient(
         @Parameter(in = ParameterIn.DEFAULT, description = "Updated client object", required=true, schema=@Schema()) 
         @Valid @RequestBody ClientRequest clientRequest);   


    @Operation(summary = "Delete client", description = "Delete client.", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "No Content"),    
        @ApiResponse(responseCode = "404", description = "Not found"),  
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/client/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteClient(
        @Parameter(in = ParameterIn.PATH, description = "The id of object to delete", required=true, schema=@Schema()) 
        @PathVariable("id") Long id);


    @Operation(summary = "Get client by id", description = "", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ClientResponse.class))),  
        @ApiResponse(responseCode = "404", description = "Not found"), 
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/client/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ClientResponse> getClientByid(
        @Parameter(in = ParameterIn.PATH, description = "Get client by id", required=true, schema=@Schema())
        @PathVariable("id") Long id);


    @Operation(summary = "Get clients", description = "", tags={ "Client" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClientResponse.class)))), 
        @ApiResponse(responseCode = "400", description = "Bad request"),   
        @ApiResponse(responseCode = "404", description = "Not found"),   
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/client",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<ClientResponse>> getClients();


}

