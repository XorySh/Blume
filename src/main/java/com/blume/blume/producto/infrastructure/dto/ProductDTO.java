package com.blume.blume.producto.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @NotBlank(message = "El campo no debe estar en blanco")
    private String nombre;

    @NotBlank(message = "El campo no debe estar en blanco")
    private String descripcion;

    @Positive
    private Float precio;

    @NotBlank(message = "El campo no debe estar en blanco")
    private Integer stock;

    @NotBlank(message = "El campo no debe estar en blanco")
    private byte[] imagen;

}
