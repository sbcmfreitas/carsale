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

import io.carsale.api.interfaces.OrderApi;
import io.carsale.dto.OrderRequest;
import io.carsale.dto.OrderResponse;
import io.carsale.model.Order;
import io.carsale.service.interfaces.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
public class OrderApiController implements OrderApi {

    private static final Logger log = LoggerFactory.getLogger(OrderApiController.class);

    @Autowired
    private OrderService orderService;

    public ResponseEntity<OrderResponse> createOrder(
        @Parameter(in = ParameterIn.DEFAULT, description = "Created order object", required=true, schema=@Schema()) 
        @Valid @RequestBody OrderRequest body) {

        try {

            OrderResponse orderResponse = this.orderService.create(new Order(body));

            return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.CREATED);

        }catch (JpaObjectRetrievalFailureException|DataIntegrityViolationException e) {

            return new ResponseEntity<OrderResponse>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<OrderResponse>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<OrderResponse> updateOrder(
        @Parameter(in = ParameterIn.DEFAULT, description = "Updated order object", required=true, schema=@Schema())
        @Valid @RequestBody OrderRequest body) {
        
        try {

            OrderResponse carResponse = this.orderService.update(new Order(body));

            return new ResponseEntity<OrderResponse>(carResponse, HttpStatus.ACCEPTED);

        }catch (EntityNotFoundException e) {

            return new ResponseEntity<OrderResponse>(HttpStatus.NOT_FOUND);
            
        }catch (JpaObjectRetrievalFailureException|DataIntegrityViolationException e) {

            return new ResponseEntity<OrderResponse>(HttpStatus.BAD_REQUEST);

        }catch (Exception e) {

            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<OrderResponse>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<OrderResponse> getOrderByid(
        @Parameter(in = ParameterIn.PATH, description = "Get order by id", required=true, schema=@Schema())
        @PathVariable("id") Long id) {
       
        try {

            OrderResponse orderResponse = this.orderService.find(id);

            return new ResponseEntity<OrderResponse>(orderResponse,HttpStatus.OK);

        } catch (JpaObjectRetrievalFailureException|EntityNotFoundException e) {

            return new ResponseEntity<OrderResponse>(HttpStatus.NOT_FOUND);

        }catch (Exception e) {

            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<OrderResponse>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<List<OrderResponse>> getOrders() {

        try {

            List<OrderResponse> orders = this.orderService.findAll();
            return new ResponseEntity<List<OrderResponse>>(orders, HttpStatus.OK);
                
        } catch (Exception e) {

            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<List<OrderResponse>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<Void> deleteOrder(
        @Parameter(in = ParameterIn.PATH, description = "The id of object to delete", required=true, schema=@Schema()) 
        @PathVariable("id") Long id) {

        try {

            this.orderService.delete(id);

            return new ResponseEntity<Void>(HttpStatus.OK);

        }catch (JpaObjectRetrievalFailureException|EntityNotFoundException e) {

            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

        }catch (Exception e) {

            log.error("Couldn't serialize response for content type application/json", e);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}