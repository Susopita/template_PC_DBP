package com.example.template_PC.entity.domain;

import jakarta.transaction.Transactional;
import org.hibernate.query.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.template_PC.entity.infrastructure.entidadRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class entidadService {

    private final entidadRepository repository;

    public entidadService(entidadRepository repository) {
        this.repository = repository;
    }

    // CRUD Básico
    @Transactional
    public entidad crear(entidad entidad) {
        return repository.save(entidad);
    }

    public Optional<entidad> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public List<entidad> obtenerTodas() {
        return repository.findAll();
    }

    @Transactional
    public Optional<entidad> actualizar(Long id, entidad entidadActualizada) {
        return repository.findById(id)
                .map(entidad -> {
                    entidad.setNombre(entidadActualizada.getNombre());
                    entidad.setDescripcion(entidadActualizada.getDescripcion());
                    entidad.setActivo(entidadActualizada.isActivo());
                    return repository.save(entidad);
                });
    }

    @Transactional
    public boolean eliminar(Long id) {
        return repository.findById(id)
                .map(entidad -> {
                    repository.delete(entidad);
                    return true;
                }).orElse(false);
    }

    public List<entidad> buscarPorNombre(String keyword) {
        return repository.findByNombreContainingIgnoreCase(keyword);
    }

    // Métodos especializados
    @Transactional
    public void desactivarEntidad(Long id) {
        repository.findById(id).ifPresent(entidad -> {
            entidad.setActivo(false);
            repository.save(entidad);
        });
    }

}
