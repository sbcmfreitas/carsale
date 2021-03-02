package io.carsale.api.interfaces;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.carsale.dto.CarRequest;
import io.carsale.dto.CarResponse;
import io.carsale.exception.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface CarApi {

    @Operation(summary = "Create new Car", description = "Create new Car", tags={ "Car" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CarResponse.class))),      
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server Error", content = @Content(schema = @Schema(implementation = ApiException.class))) })
        
    @RequestMapping(value = "/car",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<CarResponse> createCar(
        @Parameter(in = ParameterIn.DEFAULT, description = "Car object that needs to be added to the store", required=true, schema=@Schema())
        @Valid @RequestBody CarRequest body) throws Exception;


    @Operation(summary = "Update an existing car", description = "Update an existing car", tags={ "Car" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "202", description = "Accept", content = @Content(schema = @Schema(implementation = CarResponse.class))),   
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Not found"), 
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/car",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<CarResponse> updateCar(
        @Parameter(in = ParameterIn.DEFAULT, description = "Car object that needs to be added to the store", required=true, schema=@Schema())
        @Valid @RequestBody CarRequest body);



    @Operation(summary = "Delete a car", description = "Delete a car", tags={ "Car" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "No Content"),   
        @ApiResponse(responseCode = "404", description = "Not found"),  
        @ApiResponse(responseCode = "409", description = "Conflict"),
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/car/{id}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCar(
        @Parameter(in = ParameterIn.PATH, description = "Car id to delete", required=true, schema=@Schema()) 
        @PathVariable("id") Long id);


    @Operation(summary = "Find car by ID", description = "Find the car by ID", tags={ "Car" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success"),    
        @ApiResponse(responseCode = "400", description = "Bad request"),  
        @ApiResponse(responseCode = "404", description = "Not found"), 
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/car/{id}",
        method = RequestMethod.GET)
    ResponseEntity<CarResponse> findCarById(
        @Parameter(in = ParameterIn.PATH, description = "ID of car to return", required=true, schema=@Schema())
        @PathVariable("id") Long id);


    @Operation(summary = "Return a list of cars", description = "Return a list of cars", tags={ "Car" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CarResponse.class)))) })
    @RequestMapping(value = "/car",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<CarResponse>> getCars();


}