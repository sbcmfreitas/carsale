package io.carsale.repository.interfaces;

import java.util.List;

import io.carsale.model.Brand;

public interface BrandRespository {

    boolean save(Brand account);

    boolean update(Brand account);

    boolean delete(Long id);

    Brand findById(Long id);

    List<Brand> findAll();
}
