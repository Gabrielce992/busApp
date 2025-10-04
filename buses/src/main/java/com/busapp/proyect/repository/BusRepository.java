package com.busapp.proyect.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.busapp.proyect.model.Bus;
public interface BusRepository extends JpaRepository<Bus, Long> {}

