package com.blume.blume.usuario.infrastructure.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChangePasswordDTO {

    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
