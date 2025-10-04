package com.busapp.proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.busapp.proyect.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {}