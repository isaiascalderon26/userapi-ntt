package com.ntt.userapi.service.impl;


import com.ntt.userapi.dto.UserRequestDTO;
import com.ntt.userapi.dto.UserResponseDTO;
import com.ntt.userapi.entity.User;
import com.ntt.userapi.entity.Phone;
import com.ntt.userapi.repository.UserRepository;
import com.ntt.userapi.service.UserService;
import com.ntt.userapi.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Override
    @Transactional
    public UserResponseDTO crearUsuario(UserRequestDTO request) {
        userRepository.findByCorreo(request.getCorreoElectronico())
                .ifPresent( user -> {
                    throw new IllegalArgumentException("El correo ya está registrado");
                });

        String token = jwtUtil.generarToken(request.getCorreoElectronico());

        User nuevoUsuario = User.builder()
                .nombre(request.getNombreCompleto())
                .correo(request.getCorreoElectronico())
                .contraseña(request.getClaveSegura())
                .token(token)
                .activo(true)
                .ultimoLogin(LocalDateTime.now())
                .build();

        List<Phone> telefonos = request.getTelefonos().stream()
                .map(dto -> Phone.builder()
                        .numero(dto.getNumeroPrincipal())
                        .codigoCiudad(dto.getCiudadCodigo())
                        .codigoPais(dto.getPaisCodigo())
                        .usuario(nuevoUsuario)
                        .build())
                .collect(Collectors.toList());

        nuevoUsuario.setTelefonos(telefonos);

        User usuarioGuardado = userRepository.save(nuevoUsuario);


        return UserResponseDTO.builder()
                .id(usuarioGuardado.getId())
                .creado(usuarioGuardado.getCreado())
                .modificado(usuarioGuardado.getModificado())
                .ultimoLogin(usuarioGuardado.getUltimoLogin())
                .token(usuarioGuardado.getToken())
                .activo(usuarioGuardado.getActivo())
                .build();
    }

    @Override
    @Transactional
    public List<UserResponseDTO> obtenerTodos() {

        List<User> usuarios = userRepository.findAll();

        return usuarios.stream()
                .map(usuario -> UserResponseDTO.builder()
                        .id(usuario.getId())
                        .creado(usuario.getCreado())
                        .modificado(usuario.getModificado())
                        .ultimoLogin(usuario.getUltimoLogin())
                        .token(usuario.getToken())
                        .activo(usuario.getActivo())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponseDTO cbtenerPorId(UUID id) {
        User usuario = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));

        return UserResponseDTO.builder()
                .id(usuario.getId())
                .creado(usuario.getCreado())
                .modificado(usuario.getModificado())
                .ultimoLogin(usuario.getUltimoLogin())
                .token(usuario.getToken())
                .activo(usuario.getActivo())
                .build();
    }


    @Override
    @Transactional
    public UserResponseDTO actualizarUsuario(UUID id, UserRequestDTO datosActualizados){
        User usuarioExistente = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));
        if (!usuarioExistente.getCorreo().equals(datosActualizados.getCorreoElectronico())) {
            userRepository.findByCorreo(datosActualizados.getCorreoElectronico())
                    .ifPresent(user -> {
                        throw new IllegalArgumentException("El correo electrónico ya está registrado en otro usuario");
                    });
            String nuevoToken = jwtUtil.generarToken(datosActualizados.getCorreoElectronico());
            usuarioExistente.setToken(nuevoToken);
            usuarioExistente.setCorreo(datosActualizados.getCorreoElectronico());
        }

        usuarioExistente.setNombre(datosActualizados.getNombreCompleto());
        usuarioExistente.setContraseña(datosActualizados.getClaveSegura());
        usuarioExistente.setUltimoLogin(LocalDateTime.now());
        usuarioExistente.setActivo(true);

        List<Phone> nuevosTelefonos = datosActualizados.getTelefonos().stream()
                .map(dto -> Phone.builder()
                        .numero(dto.getNumeroPrincipal())
                        .codigoCiudad(dto.getCiudadCodigo())
                        .codigoPais(dto.getPaisCodigo())
                        .usuario(usuarioExistente)
                        .build())
                .collect(Collectors.toList());
        usuarioExistente.getTelefonos().clear();
        usuarioExistente.getTelefonos().addAll(nuevosTelefonos);

        User actualizado = userRepository.save(usuarioExistente);

                return UserResponseDTO.builder()
                        .id(actualizado.getId())
                        .creado(actualizado.getCreado())
                        .modificado(actualizado.getModificado())
                        .ultimoLogin(actualizado.getUltimoLogin())
                        .token(actualizado.getToken())
                        .build();
    }

    @Override
    @Transactional
    public UserResponseDTO actualizarParcial(UUID id, UserRequestDTO cambios){
        User usuario = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));

        if (cambios.getNombreCompleto() != null){
            usuario.setNombre(cambios.getNombreCompleto());
        }

        if (cambios.getCorreoElectronico() != null && !usuario.getCorreo().equals(cambios.getCorreoElectronico())){
            userRepository.findByCorreo(cambios.getCorreoElectronico())
                    .ifPresent(user -> {
                        throw new IllegalArgumentException("El correo electrónico ya esta registrado en otro usuario");
                    });
            usuario.setCorreo(cambios.getCorreoElectronico());
            usuario.setToken(jwtUtil.generarToken(cambios.getCorreoElectronico()));
        }

        if (cambios.getClaveSegura() != null){
            usuario.setContraseña(cambios.getClaveSegura());
        }

        if (cambios.getTelefonos()!= null && !cambios.getTelefonos().isEmpty()){
            List<Phone> telefonosActualizados = cambios.getTelefonos().stream()
                    .map(dto -> Phone.builder()
                            .numero(dto.getNumeroPrincipal())
                            .codigoCiudad(dto.getCiudadCodigo())
                            .codigoPais(dto.getPaisCodigo())
                            .usuario(usuario)
                            .build())
                    .collect(Collectors.toList());
            usuario.getTelefonos().clear();
            usuario.getTelefonos().addAll(telefonosActualizados);

        }

        usuario.setUltimoLogin(LocalDateTime.now());

        User actualizado = userRepository.save(usuario);

        return UserResponseDTO.builder()
                .id(actualizado.getId())
                .creado(actualizado.getCreado())
                .modificado(actualizado.getModificado())
                .ultimoLogin(actualizado.getUltimoLogin())
                .token(actualizado.getToken())
                .activo(actualizado.getActivo())
                .build();
    }


    @Override
    @Transactional
    public void eliminarUsuario(UUID id){
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        userRepository.delete(usuario);
    }




}
