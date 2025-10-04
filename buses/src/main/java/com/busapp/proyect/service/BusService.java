package com.busapp.proyect.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.busapp.proyect.dto.BusDTO;
import java.util.Optional;

public interface BusService {
    // Listar buses con paginación
    Page<BusDTO> listar(Pageable pageable);

    // Obtener un bus por ID (opcional)
    Optional<BusDTO> obtenerPorId(Long id);

    // Obtener un bus o lanzar excepción si no existe
    BusDTO obtenerPorIdOrThrow(Long id);

    // Crear un nuevo bus
    BusDTO crear(BusDTO dto);
}
