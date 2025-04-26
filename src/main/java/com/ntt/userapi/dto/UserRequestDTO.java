package com.ntt.userapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {


    private String nombreCompleto;


    @Email
    private String correoElectronico;


    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, incluyendo letras y números"
    )
    private String claveSegura;

   private List<PhoneDTO> telefonos;

}
