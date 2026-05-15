package com.gym.Clientes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.Clientes.model.cliente;
import com.gym.Clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    //GET /api/usuarios/ -> 200 OK 
    @GetMapping
    public ResponseEntity<List<cliente>> obtenerTodos(){
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    //GET /api/usuarios/{id} → 200 OK o 404 NOT FOUND
    @GetMapping("/{id}")
    public ResponseEntity<cliente> obtenerPorId(@PathVariable Long id){
        return clienteService.obtenerPorId(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    //POST /api/usuarios → 201 Created
    @PostMapping
    public ResponseEntity<cliente> crearCliente(@Valid @RequestBody cliente cli) {
        cliente nuevo = clienteService.crearCliente(cli);
        return ResponseEntity.status(201).body(nuevo);
    }

    //PUT /api/usuarios/{id} → 200 OK o 404
    @PutMapping("/{id}")
    public ResponseEntity<cliente> actualizarCliente(@PathVariable Long id, @Valid @RequestBody cliente cli  ){
        return clienteService.actualizarCliente(id, cli)
        .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/usuarios/{id} → 204 o 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id){
        if (clienteService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
    // POST /api/usuarios/verificar → verifica credenciales
    @PostMapping("/verificar")
    public ResponseEntity<String> verificar(@RequestBody cliente loginReq){
        boolean valido =  clienteService.verificarCliente(loginReq.getNombre(), loginReq.getPassword());
        return valido ? ResponseEntity.ok("Acceso Concedido") : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credencial Invalidas");
    }


}
