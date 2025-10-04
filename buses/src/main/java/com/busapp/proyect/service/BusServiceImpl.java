package com.busapp.proyect.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.busapp.proyect.repository.BusRepository;
import com.busapp.proyect.repository.BrandRepository;
import com.busapp.proyect.dto.BusDTO;
import com.busapp.proyect.model.Bus;
import com.busapp.proyect.model.Brand;
import com.busapp.proyect.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;
    private final BrandRepository brandRepository;

    @Override
    public Page<BusDTO> listar(Pageable pageable) {
        return busRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public Optional<BusDTO> obtenerPorId(Long id) {
        return busRepository.findById(id).map(this::toDto);
    }

    @Override
    public BusDTO obtenerPorIdOrThrow(Long id) {
        Bus b = busRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus con id " + id + " no encontrado"));
        return toDto(b);
    }

    @Override
    @Transactional
    public BusDTO crear(BusDTO dto) {
        Bus b = new Bus();
        // Asumo que BusDTO tiene campos públicos (numeroBus, placa, caracteristicas, estado, marcaId).
        b.setNumeroBus(dto.numeroBus);
        b.setPlaca(dto.placa);
        b.setCaracteristicas(dto.caracteristicas);
        // Si la entidad inicializa fechaCreacion por defecto, no la tocamos aquí.
        b.setEstado(dto.estado == null ? "ACTIVO" : dto.estado);

        if (dto.marcaId != null) {
            Brand marca = brandRepository.findById(dto.marcaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Marca con id " + dto.marcaId + " no encontrada"));
            b.setMarca(marca);
        } else {
            b.setMarca(null);
        }

        Bus saved = busRepository.save(b);
        return toDto(saved);
    }

    private BusDTO toDto(Bus b) {
        BusDTO d = new BusDTO();
        d.id = b.getId();
        d.numeroBus = b.getNumeroBus();
        d.placa = b.getPlaca();
        d.fechaCreacion = b.getFechaCreacion();
        d.caracteristicas = b.getCaracteristicas();
        if (b.getMarca() != null) {
            d.marcaId = b.getMarca().getId();
            d.marcaNombre = b.getMarca().getNombre();
        } else {
            d.marcaId = null;
            d.marcaNombre = null;
        }
        d.estado = b.getEstado();
        return d;
    }
}
