package com.example.template_PC.entity.application;

import com.example.template_PC.entity.domain.entidad;
import com.example.template_PC.entity.infrastructure.entidadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/entidades") // Cambia "/entidades" por tu endpoint (ej: "/productos")
public class entidadController {

    @Autowired
    private entidadRepository entidadRepository;

    // 1. Crear nueva entidad (POST)
    @PostMapping
    public ResponseEntity<entidad> crear(@RequestBody entidad entidad) {
        entidad nuevaEntidad = entidadRepository.save(entidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEntidad);
    }

    // 2. Obtener todas las entidades (GET)
    @GetMapping
    public ResponseEntity<List<entidad>> obtenerTodas() {
        return ResponseEntity.ok(entidadRepository.findAll());
    }

    // 3. Obtener entidad por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<entidad> obtenerPorId(@PathVariable Long id) {
        return entidadRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Actualizar entidad completa (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<entidad> actualizarCompleta(
            @PathVariable Long id,
            @RequestBody entidad entidad) {
        if (!entidadRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entidad.setId(id); // Asegura que el ID coincida
        return ResponseEntity.ok(entidadRepository.save(entidad));
    }

    // 5. Actualización parcial (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<entidad> actualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> campos) {
        Optional<entidad> entidadExistente = entidadRepository.findById(id);
        if (entidadExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Actualización dinámica de campos
        campos.forEach((campo, valor) -> {
            switch (campo) {
                case "nombre" -> entidadExistente.get().setNombre((String) valor);
                case "activo" -> entidadExistente.get().setActivo((Boolean) valor);
                // Añade más campos según tu entidad
            }
        });

        return ResponseEntity.ok(entidadRepository.save(entidadExistente.get()));
    }

    // 6. Eliminar entidad (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!entidadRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entidadRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 7. Búsqueda paginada (GET)
    @GetMapping("/pagina")
    public ResponseEntity<Page<entidad>> obtenerPagina(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamaño,
            @RequestParam(defaultValue = "id") String ordenarPor) {
        Pageable pageable = PageRequest.of(pagina, tamaño, Sort.by(ordenarPor));
        return ResponseEntity.ok(entidadRepository.findAll(pageable));
    }

    // 8. Filtrar por campo exacto (GET)
    @GetMapping("/buscar")
    public ResponseEntity<List<entidad>> filtrarPorCampo(
            @RequestParam String campo,
            @RequestParam String valor) {
        List<entidad> resultados;
        switch (campo.toLowerCase()) {
            case "nombre" -> resultados = entidadRepository.findByNombreContaining(valor);
            case "activo" -> resultados = entidadRepository.findByActivo(Boolean.parseBoolean(valor));
            default -> throw new IllegalArgumentException("Campo no soportado: " + campo);
        }
        return ResponseEntity.ok(resultados);
    }

    // 9. Búsqueda personalizada con Query (POST para filtros complejos)
    @PostMapping("/busqueda")
    public ResponseEntity<List<entidad>> busquedaPersonalizada(
            @RequestBody Map<String, Object> filtros) {
        // Ejemplo: Implementar lógica con Specifications o QueryDSL
        return ResponseEntity.ok(entidadRepository.findAll()); // Reemplazar con tu lógica
    }

    // 10. Obtener entidades activas (GET)
    @GetMapping("/activas")
    public ResponseEntity<List<entidad>> obtenerActivas() {
        return ResponseEntity.ok(entidadRepository.findByActivoTrue());
    }

    // 11. Contar total de entidades (GET)
    @GetMapping("/total")
    public ResponseEntity<Long> contarTotal() {
        return ResponseEntity.ok(entidadRepository.count());
    }

    // 12. Exportar a CSV (GET)
    @GetMapping("/exportar-csv")
    public ResponseEntity<String> exportarCSV() {
        List<entidad> entidades = entidadRepository.findAll();
        // Lógica para convertir a CSV (simplificado)
        StringBuilder csv = new StringBuilder("id,nombre,activo\n");
        entidades.forEach(e -> csv.append(e.getId()).append(",")
                .append(e.getNombre()).append(",")
                .append(e.isActivo()).append("\n"));
        return ResponseEntity.ok()
                .header("Content-Type", "text/csv")
                .header("Content-Disposition", "attachment; filename=entidades.csv")
                .body(csv.toString());
    }

    // 13. Restaurar eliminación lógica (PATCH)
    @PatchMapping("/restaurar/{id}")
    public ResponseEntity<entidad> restaurar(@PathVariable Long id) {
        Optional<entidad> entidad = entidadRepository.findById(id);
        if (entidad.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entidad.get().setActivo(true); // Asume borrado lógico con campo 'activo'
        return ResponseEntity.ok(entidadRepository.save(entidad.get()));
    }

    // 14. Obtener histórico de cambios (GET) - Requiere tabla de auditoría
    @GetMapping("/{id}/historico")
    public ResponseEntity<List<Object>> obtenerHistorico(@PathVariable Long id) {
        // Ejemplo: Integrar con Hibernate Envers o tabla de logs
        return ResponseEntity.ok(List.of("Módulo de auditoría no implementado"));
    }
}