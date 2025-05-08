package com.example.template_PC.infrastructure;

import com.example.template_PC.domain.entidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface entidadRepository extends JpaRepository<entidad, Long> {


    List<entidad> findByNombre(String nombre);
    List<entidad> findByNombreContainingIgnoreCase(String keyword);
    List<entidad> findByDescripcionIsNull();
    List<entidad> findByActivo(boolean activo);
    List<entidad> findByNombreContaining(String keyword);
    List<entidad> findByActivoTrue();
}
