package com.gym.Clientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.gym.Clientes.model.cliente;
import com.gym.Clientes.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    // ── OBTENER TODOS ────────────────────────────────
    public List<cliente> obtenerTodos(){
        return clienteRepository.findAll();
    }

    // ── OBTENER POR ID ───────────────────────────────
    public Optional<cliente> obtenerPorId(@NonNull Long id){
        return clienteRepository.findById(id);
    }

    // ── CREAR ──────────────────────────────────────
    @Transactional
    public cliente crearCliente(@NonNull cliente cli){
        return clienteRepository.save(cli);
    }

    // ── ACTUALIZAR ───────────────────────────────────
    public Optional<cliente> actualizarCliente(@NonNull Long id, cliente cli){
        return clienteRepository.findById(id).map(existente -> {
            existente.setRut(cli.getRut());
            existente.setNombre(cli.getNombre());
            existente.setPassword(cli.getPassword());
            existente.setCorreo(cli.getCorreo());
            existente.setTelefono(cli.getTelefono());
            return clienteRepository.save(existente);
        });
    }

    // ── ELIMINAR ─────────────────────────────────────
    public void eliminarCliente(@NonNull Long id){
        clienteRepository.deleteById(id);
    }

    // ── VERIFICAR USUARIO ────────────────────────────
    public boolean verificarCliente(String nombre, String password){
        return clienteRepository.findByNombre(nombre).map(cliente -> 
            cliente.getPassword().equals(password)).orElse(false);
    }

}
