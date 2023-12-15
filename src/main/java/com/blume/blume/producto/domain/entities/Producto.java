package com.blume.blume.producto.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
