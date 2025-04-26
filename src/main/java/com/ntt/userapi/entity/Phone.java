package com.ntt.userapi.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "telefonos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String codigoCiudad;
    private String codigoPais;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private  User usuario;
}
