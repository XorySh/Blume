package com.blume.blume.usuario.domain.entities;

import com.blume.blume.jwt.token.RefreshToken;
import com.blume.blume.usuario.domain.interfaces.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuario")
public class Usuario implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email(message = "El campo debe ser un email")
    @NotBlank(message = "El campo no debe estar en blanco")
    private String email;

    @NotBlank
    @Size(min = 6,  message = "La contraseña debe de contener al menos 6 carácteres")
    private String password;

    @NotBlank(message = "El campo no debe estar en blanco")
    private String genero;

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

    @Enumerated(EnumType.STRING)
    private Role rol;

    @OneToMany(mappedBy = "usuario")
    private List<RefreshToken> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




}
