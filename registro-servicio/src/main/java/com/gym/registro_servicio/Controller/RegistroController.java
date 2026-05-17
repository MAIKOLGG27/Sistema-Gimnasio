package com.gym.registro_servicio.Controller;

import com.gym.registro_servicio.Model.Registro;
import com.gym.registro_servicio.Service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/registros")
public class RegistroController {

    @Autowired
    private RegistroService servicio;

    @GetMapping("/listar")
    public List<Registro> traerTodos(){
        return servicio.obtenerTodas();
    }

    @GetMapping("/{id}")                    
    public Optional<Registro> buscarPorId(@PathVariable Long id){
        return servicio.buscarPorId(id);
    }

    @PostMapping("/crear")
    public Registro crearRegistro(@RequestBody Registro registro) {
        return servicio.registrarRegistro(registro);
    }

    @PutMapping("/{id}")                   
    public Registro actualizarRegistro(@PathVariable Long id, 
                                        @RequestBody Registro registro){
        return servicio.actualizarRegistro(id, registro);
    }

    @DeleteMapping("/borrar/{id}")
    public void eliminarRegistro(@PathVariable Long id){
        servicio.borrarRegistro(id);
    }

    @GetMapping("/conPago/{id}")
    public Map buscarConPago(@PathVariable Long id) {
        return servicio.obtenerRegistroConPago(id);
    }
}