package com.ntt.userapi.service;

import com.ntt.userapi.dto.UserRequestDTO;
import com.ntt.userapi.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO crearUsuario(UserRequestDTO request);

    List<UserResponseDTO> obtenerTodos();

    UserResponseDTO cbtenerPorId(UUID id);

    UserResponseDTO actualizarUsuario(UUID id, UserRequestDTO datosActualizados);

    UserResponseDTO actualizarParcial (UUID id, UserRequestDTO cambios);

    void eliminarUsuario(UUID id);
}
