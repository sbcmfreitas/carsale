package io.carsale.service.interfaces;

import java.util.List;

import io.carsale.model.Brand;

public interface BrandService {

    Brand create(Brand brand);

	Brand update(Brand brand);

	boolean delete(Long id);

	Brand find(Long id);

    List<Brand> findAll();  
}
