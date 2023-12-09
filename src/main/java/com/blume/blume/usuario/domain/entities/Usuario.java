package com.blume.blume.usuario.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email(message = "El campo debe ser un email")
    @NotBlank(message = "El campo no debe estar en blanco")
    private String correo;

    @NotBlank(message = "El campo no debe estar en blanco")
    @Size(min = 4, max = 50, message = "El nombre debe de contener al menos 5 carácteres")
    private String nombre;

    @NotBlank(message = "El campo no debe estar en blanco")
    private String apellido;

    @NotBlank(message = "El campo no debe estar en blanco")
    @Column(name = "apellido_dos")
    private String apellidoDos;

    @NotBlank(message = "El campo no debe estar en blanco")
    private String telefono;

    @NotBlank(message = "El campo no debe estar en blanco")
    private String localidad;

    @NotBlank(message = "El campo no debe estar en blanco")
    private String provincia;

    @NotBlank(message = "El campo no debe estar en blanco")
    private String pais;

    @NotBlank(message = "El campo no debe estar en blanco")
    private String direccion;

    private Boolean activo;

    @PastOrPresent(message = "La fecha de registro no puede ser superior a la de hoy")
    @Column(name = "fecha_registro")
    private Date fechaRegistro;

}
