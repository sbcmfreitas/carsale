package io.carsale.api.interfaces;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.carsale.dto.OrderRequest;
import io.carsale.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface OrderApi {

    @Operation(summary = "Create order", description = "Create the order.", tags={ "Order" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/order",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<OrderResponse> createOrder(
        @Parameter(in = ParameterIn.DEFAULT, description = "Created order object", required=true, schema=@Schema()) 
        @Valid @RequestBody OrderRequest body);



    @Operation(summary = "Updated order", description = "Updated order.", tags={ "Order" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "202", description = "Accepted", content = @Content(schema = @Schema(implementation = OrderResponse.class))),  
        @ApiResponse(responseCode = "400", description = "Bad request"), 
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/order",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<OrderResponse> updateOrder(
         @Parameter(in = ParameterIn.DEFAULT, description = "Updated order object", required=true, schema=@Schema()) 
         @Valid @RequestBody OrderRequest body);   


    @Operation(summary = "Delete order", description = "Delete order.", tags={ "Order" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "No Content"),    
        @ApiResponse(responseCode = "404", description = "Not found"),  
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/order/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteOrder(
        @Parameter(in = ParameterIn.PATH, description = "The id of object to delete", required=true, schema=@Schema()) 
        @PathVariable("id") Long id);


    @Operation(summary = "Get order by id", description = "", tags={ "Order" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = OrderResponse.class))),  
        @ApiResponse(responseCode = "404", description = "Not found"), 
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/order/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<OrderResponse> getOrderByid(
        @Parameter(in = ParameterIn.PATH, description = "Get order by id", required=true, schema=@Schema())
        @PathVariable("id") Long id);


    @Operation(summary = "Get orders", description = "", tags={ "Order" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))), 
        @ApiResponse(responseCode = "404", description = "Not found"),   
        @ApiResponse(responseCode = "500", description = "Internal server Error") })
    @RequestMapping(value = "/order",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<OrderResponse>> getOrders();


}

