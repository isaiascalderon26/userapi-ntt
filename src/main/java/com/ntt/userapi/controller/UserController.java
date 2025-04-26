package com.ntt.userapi.controller;


import com.ntt.userapi.dto.UserRequestDTO;
import com.ntt.userapi.dto.UserResponseDTO;
import com.ntt.userapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UserRequestDTO request) {
        try {
            UserResponseDTO response = userService.crearUsuario(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"mensaje\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping
    public  ResponseEntity<List<UserResponseDTO>> listarUsuarios(){
        List<UserResponseDTO> usuarios = userService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable UUID id) {
        try{
            UserResponseDTO usuario = userService.cbtenerPorId(id);
            return  ResponseEntity.ok(usuario);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Collections.singletonMap("mensaje", e.getMessage()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequestDTO datosActualizados ) {
        try {
            UserResponseDTO usuarioActualizado = userService.actualizarUsuario(id, datosActualizados);
            return ResponseEntity.ok(usuarioActualizado);
        }catch (IllegalArgumentException e){
            HttpStatus status = e.getMessage().contains("no encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                                 .body(Collections.singletonMap("mensaje", e.getMessage()));
        }


    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcialUsuario(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequestDTO cambios ) {
        try {
            UserResponseDTO usuarioActualizado = userService.actualizarParcial(id, cambios);
            return ResponseEntity.ok(usuarioActualizado);
        }catch (IllegalArgumentException e){
            HttpStatus status = e.getMessage().contains("no encontrado") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(Collections.singletonMap("mensaje", e.getMessage()));
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable UUID id) {
        try{
            userService.eliminarUsuario(id);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Usuario eliminado"));
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Collections.singletonMap("mensaje", e.getMessage()));
        }

    }
}
