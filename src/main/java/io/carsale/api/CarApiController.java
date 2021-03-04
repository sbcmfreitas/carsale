package io.carsale.api;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.carsale.adapter.ModelMapperFactory;
import io.carsale.api.interfaces.CarApi;
import io.carsale.dto.CarRequest;
import io.carsale.dto.CarResponse;
import io.carsale.exception.OptionQuantityRestrictionException;
import io.carsale.model.Car;
import io.carsale.service.interfaces.CarService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
public class CarApiController implements CarApi {

    @Autowired
    private CarService carService;

    private ModelMapper modelMapper = ModelMapperFactory.getInstance();

    public ResponseEntity<CarResponse> createCar(
        @Parameter(in = ParameterIn.DEFAULT, description = "Car object that needs to be added to the store", required=true, schema=@Schema()) 
        @Valid @RequestBody CarRequest carRequest) throws Exception {
        
        try {

            Car car = modelMapper.map(carRequest, Car.class);

            CarResponse carResponse = this.carService.create(car);

            return new ResponseEntity<CarResponse>(carResponse, HttpStatus.CREATED);

        }catch (OptionQuantityRestrictionException|JpaObjectRetrievalFailureException|DataIntegrityViolationException e) {

            return new ResponseEntity<CarResponse>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            throw e;

        }

    }

    public ResponseEntity<CarResponse> updateCar(
          @Parameter(in = ParameterIn.DEFAULT, description = "Car object that needs to be added to the store", required=true, schema=@Schema()) 
          @Valid @RequestBody CarRequest carRequest) {
       
        try {

            Car car = modelMapper.map(carRequest, Car.class);

            CarResponse carResponse = this.carService.update(car);

            return new ResponseEntity<CarResponse>(carResponse, HttpStatus.ACCEPTED);

        }catch (EntityNotFoundException e) {

            return new ResponseEntity<CarResponse>(HttpStatus.NOT_FOUND);
            
        }catch (OptionQuantityRestrictionException|JpaObjectRetrievalFailureException|DataIntegrityViolationException e) {

            return new ResponseEntity<CarResponse>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            throw e;

        }

    }

    public ResponseEntity<Void> deleteCar(
        @Parameter(in = ParameterIn.PATH, description = "Car id to delete", required=true, schema=@Schema()) 
        @PathVariable("id") Long id) {
        
        try {

            if(this.carService.delete(id)){
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch (JpaObjectRetrievalFailureException|EntityNotFoundException e) {

            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {

            throw e;

        }

    }


    public ResponseEntity<CarResponse> findCarById(
        @Parameter(in = ParameterIn.PATH, description = "ID of car to return", required=true, schema=@Schema()) 
        @PathVariable("id") Long id) {
       
        try {

            CarResponse carResponse = this.carService.find(id);

            return new ResponseEntity<CarResponse>(carResponse,HttpStatus.OK);

        } catch (JpaObjectRetrievalFailureException|EntityNotFoundException e) {

            return new ResponseEntity<CarResponse>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {

            throw e;

        }
       
    }

    public ResponseEntity<List<CarResponse>> getCars() {

        try {

            List<CarResponse> cars = this.carService.findAll();
            return new ResponseEntity<List<CarResponse>>(cars, HttpStatus.OK);
                
        } catch (Exception e) {

            throw e;

        }

    }


   
}
