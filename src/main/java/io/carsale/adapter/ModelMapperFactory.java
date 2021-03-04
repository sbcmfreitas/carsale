package io.carsale.adapter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import io.carsale.dto.BrandResponse;
import io.carsale.dto.CarRequest;
import io.carsale.dto.CarResponse;
import io.carsale.dto.ClientRequest;
import io.carsale.dto.ClientResponse;
import io.carsale.dto.OptionResponse;
import io.carsale.dto.OrderRequest;
import io.carsale.dto.OrderResponse;
import io.carsale.model.Brand;
import io.carsale.model.Car;
import io.carsale.model.Client;
import io.carsale.model.Option;
import io.carsale.model.Order;

@Component
public class ModelMapperFactory {

    private static ModelMapper modelMapper;

    private ModelMapperFactory() {
    }

    public static ModelMapper getInstance() 
    { 
        if (modelMapper == null){
          modelMapper = new ModelMapper(); 
          modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
          modelMapper.getConfiguration().setAmbiguityIgnored(true);
          CarMapperConfiguration();
          ClientMapperConfiguration();
          OrderMapperConfiguration();
        }

      return modelMapper;
    }

    private static void CarMapperConfiguration() {

        modelMapper.createTypeMap(CarRequest.class, Car.class)
                   .addMappings(
                    mapper -> {
                        mapper.map(src -> src.getId(), Car::setId);
                        mapper.map(src -> src.getBrandId(), Car::setBrand);
                        mapper.map(src -> src.getOptionIds(), Car::setOptions);
                        mapper.map(src -> src.getValue(), Car::setValue);
                    });

        modelMapper.createTypeMap(Long.class, Brand.class)
                   .addMappings(
                    mapper -> {
                         mapper.map(src -> src, Brand::setId);
                    });

        modelMapper.createTypeMap(Long.class, Option.class)
                    .addMappings(
                      mapper -> {
                          mapper.map(src -> src, Option::setId);
                    });

        modelMapper.createTypeMap(Car.class, CarResponse.class)
                    .addMappings(
                     mapper -> {
                         mapper.map(src -> src.getId(), CarResponse::setId);
                         mapper.map(src -> src.getBrand(), CarResponse::setBrand);
                         mapper.map(src -> src.getOptions(), CarResponse::setOptions);
                         mapper.map(src -> src.getValue(), CarResponse::setValue);
                    }); 

        modelMapper.createTypeMap(Brand.class, BrandResponse.class)
                   .addMappings(
                    mapper -> {
                           mapper.map(src -> src.getId(), BrandResponse::setId);
                           mapper.map(src -> src.getName(), BrandResponse::setName);
                    });

        modelMapper.createTypeMap(Option.class, OptionResponse.class)
                    .addMappings(
                    mapper -> {
                             mapper.map(src -> src.getId(), OptionResponse::setId);
                             mapper.map(src -> src.getName(), OptionResponse::setName);
                    });

    }

    private static void ClientMapperConfiguration() {

        modelMapper.createTypeMap(ClientRequest.class, Client.class)
                   .addMappings(
                    mapper -> {
                        mapper.map(src -> src.getId(), Client::setId);
                        mapper.map(src -> src.getEmail(), Client::setEmail);
                        mapper.map(src -> src.getFirstName(), Client::setFirstName);
                        mapper.map(src -> src.getLastName(), Client::setLastName);
                        mapper.map(src -> src.getPhone(), Client::setPhone);
                    });

        modelMapper.createTypeMap(Client.class, ClientResponse.class)
                    .addMappings(
                     mapper -> {
                         mapper.map(src -> src.getFirstName(), ClientResponse::setName);
                         mapper.map(src -> src.getEmail(), ClientResponse::setEmail);
                         mapper.map(src -> src.getId(), ClientResponse::setId);
                         mapper.map(src -> src.getPhone(), ClientResponse::setPhone);
                     });

    }

    private static void OrderMapperConfiguration() {

        modelMapper.createTypeMap(OrderRequest.class, Order.class)
                   .addMappings(
                    mapper -> {
                        mapper.map(src -> src.getId(), Order::setId);
                        mapper.map(src -> src.getClientId(), Order::setClient);
                        mapper.map(src -> src.getCarId(), Order::setCar);
                        mapper.map(src -> src.getOrderDate(), Order::setOrderDate);
                        mapper.map(src -> src.getStatus(), Order::setStatus);
                        mapper.map(src -> src.getValue(), Order::setValue);
                    });

        modelMapper.createTypeMap(Long.class, Client.class)
                    .addMappings(
                     mapper -> {
                         mapper.map(src -> src, Client::setId);
                     });

        modelMapper.createTypeMap(Long.class, Car.class)
                     .addMappings(
                      mapper -> {
                          mapper.map(src -> src, Car::setId);
                      });
                      
        modelMapper.createTypeMap(Order.class, OrderResponse.class)
                      .addMappings(
                       mapper -> {
                           mapper.map(src -> src.getId(), OrderResponse::setId);
                           mapper.map(src -> src.getClient(), OrderResponse::setClient);
                           mapper.map(src -> src.getCar(), OrderResponse::setCar);
                           mapper.map(src -> src.getOrderDate(), OrderResponse::setOrderDate);
                           mapper.map(src -> src.getStatus(), OrderResponse::setStatus);
                           mapper.map(src -> src.getValue(), OrderResponse::setValue);
                       });


    }


}