package com.ntt.userapi.dto;


import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDTO {

    private String numeroPrincipal;
    private String ciudadCodigo;
    private String paisCodigo;
}
