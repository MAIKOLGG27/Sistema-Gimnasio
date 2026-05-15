package com.gym.asistencia_servicio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.asistencia_servicio.model.AsistenciaModel;
import com.gym.asistencia_servicio.service.AsistenciaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService servicio;

    @GetMapping("/Listar")
    public List<AsistenciaModel> traerTodos(){
        return servicio.obtenerTodas();
    }

    @PostMapping("/Registrar")
    public AsistenciaModel guardarAsistncia(@RequestBody AsistenciaModel asistencia){
        return servicio.registrarAsistencia(asistencia);
    }

    @PutMapping("/Actualizar")
    public AsistenciaModel actualizar(@PathVariable Long id, @RequestBody AsistenciaModel asistencia){
        return servicio.actualizarAsistencia(id, asistencia);
    }

    @DeleteMapping("/Eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        servicio.borrarAsistencia(id);
    }
    
    
    
    

    
    

}
