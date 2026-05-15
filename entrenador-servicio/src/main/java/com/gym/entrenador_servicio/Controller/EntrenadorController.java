package com.gym.entrenador_servicio.Controller;

import com.gym.entrenador_servicio.Model.Entrenador;
import com.gym.entrenador_servicio.Service.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorService servicio;

    @GetMapping("/Listar")
    public List<Entrenador> traerTodos(){
        return servicio.obtenerTodas();
    }

    @GetMapping("/{id}")                   
    public Optional<Entrenador> buscarPorId(@PathVariable Long id){
        return servicio.buscarPorId(id);
    }

    @PostMapping("/crear")
    public Entrenador crearEntrenador(@RequestBody Entrenador entrenador) {
        return servicio.registrarEntrenador(entrenador);
    }

    @PutMapping("/{id}")
    public Entrenador actualizarEntrenador(@PathVariable Long id, 
                                           @RequestBody Entrenador entrenador){
        return servicio.actualizarEntrenador(id, entrenador);
    }

    @DeleteMapping("/borrar/{id}")
    public void eliminarEntrenador(@PathVariable Long id){
        servicio.borrarEntrenador(id);
    }
}
