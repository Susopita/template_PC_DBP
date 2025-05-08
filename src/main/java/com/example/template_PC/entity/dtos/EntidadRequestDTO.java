package com.example.template_PC.entity.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Puedes renombrar esta clase según el propósito específico, por ejemplo: UserDTO, ProductDTO, etc.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntidadRequestDTO {
    // Ejemplo de campo obligatorio con validaciones
    @NotNull(message = "El ID no puede ser nulo")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 120, message = "La edad no puede superar los 120 años")
    private Integer edad;

    // Otros campos según sea necesario
}
