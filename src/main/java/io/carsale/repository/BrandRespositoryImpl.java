package io.carsale.repository;

import org.springframework.stereotype.Repository;

import io.carsale.model.Brand;
import io.carsale.repository.interfaces.BrandRespository;

@Repository
public class BrandRespositoryImpl extends AbstractRepository<Brand, Long> implements BrandRespository{
}
