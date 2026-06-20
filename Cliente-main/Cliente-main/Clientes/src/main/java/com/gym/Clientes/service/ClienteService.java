package com.gym.Clientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym.Clientes.model.cliente;
import com.gym.Clientes.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {
    private final ClienteRepository clienteRepository;

    // ── OBTENER TODOS ────────────────────────────────
    public List<cliente> obtenerTodos(){
        return clienteRepository.findAll();
    }

    // ── OBTENER POR ID ───────────────────────────────
    public Optional<cliente> obtenerPorId(Long id){
        return clienteRepository.findById(id);
    }

    // ── CREAR ──────────────────────────────────────
    public cliente crearCliente(cliente cli){
        return clienteRepository.save(cli);
    }

    // ── ACTUALIZAR ───────────────────────────────────
    public Optional<cliente> actualizarCliente(Long id, cliente cli){
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
    public void eliminarCliente(Long id){
        clienteRepository.deleteById(id);
    }



}
