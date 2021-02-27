package io.carsale.adapter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import io.carsale.dto.BrandResponse;
import io.carsale.dto.CarResponse;
import io.carsale.dto.OptionResponse;
import io.carsale.model.Car;

public class CarResponseAdapter extends CarResponse {

    private Car car = null;

    public CarResponseAdapter(Car car) {
        super();
        this.car = car;
    }

    @Override
    public BrandResponse getBrand() {
        return this.car.getBrand()!=null ? new BrandResponse(this.car.getBrand()) : null;
    }

    @Override
    public BigDecimal getValue() {
        return this.car.getValue();
    }

    @Override
    public List<OptionResponse> getOptions() {
        return this.car.getOptions()!=null ? this.car.getOptions().stream().map(p -> new OptionResponse(p)).collect(Collectors.toList()) : null;
    }

    @Override
    public Long getId() {
        return this.car.getId();
    }

    
}
