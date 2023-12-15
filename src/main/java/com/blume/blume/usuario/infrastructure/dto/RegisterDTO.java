package com.blume.blume.usuario.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDTO implements Serializable {

    @Email
    private String email;
    @NotBlank
    private String genero;
    @NotBlank
    private String password;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String apellidoDos;
    @NotBlank
    private String telefono;
    @NotBlank
    private String localidad;
    @NotBlank
    private String provincia;
    @NotBlank
    private String pais;
    @NotBlank
    private String direccion;


}
