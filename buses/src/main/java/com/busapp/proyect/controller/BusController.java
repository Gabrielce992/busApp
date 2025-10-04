package com.busapp.proyect.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import com.busapp.proyect.service.BusService;
import com.busapp.proyect.dto.BusDTO;

import java.net.URI;

@RestController
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;

    /**
     * Listar todos los buses paginados
     */
    @GetMapping
    public ResponseEntity<Page<BusDTO>> listar(Pageable pageable) {
        Page<BusDTO> page = busService.listar(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * Obtener un bus por su ID
     * Lanza ResourceNotFoundException si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> obtener(@PathVariable Long id) {
        BusDTO bus = busService.obtenerPorIdOrThrow(id);
        return ResponseEntity.ok(bus);
    }

    /**
     * Crear un nuevo bus
     * Retorna 201 Created con la ubicación del nuevo recurso
     */
    @PostMapping
    public ResponseEntity<BusDTO> crear(@RequestBody BusDTO dto) {
        BusDTO creado = busService.crear(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id)
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }
}
