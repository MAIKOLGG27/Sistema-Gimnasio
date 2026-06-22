package com.gym.membrecias.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gym.membrecias.model.membreciaModel;
import com.gym.membrecias.repository.membreciaRepository;

@ExtendWith(MockitoExtension.class) 
public class membreciaServiceTest {

    @Mock
    private membreciaRepository repo; 

    @InjectMocks
    private membreciaService service; 

    private membreciaModel membresiaMock;

    @BeforeEach
    void setUp() {
        
        membresiaMock = new membreciaModel();
        membresiaMock.setId(1L);
        membresiaMock.setNombre("Membresía Premium");
        membresiaMock.setPrecio(50000.0);
        membresiaMock.setDuracionDias(30);
    }

    @Test
    void obtenerPorId_DebeRetornarMembresia_CuandoExiste() {
        
        when(repo.findById(1L)).thenReturn(Optional.of(membresiaMock));

        
        membreciaModel resultado = service.obtenerPorId(1L);

       
        assertNotNull(resultado);
        assertEquals("Membresía Premium", resultado.getNombre());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_DebeLanzarExcepcion_CuandoNoExiste() {
        
        when(repo.findById(99L)).thenReturn(Optional.empty());

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.obtenerPorId(99L);
        });

        assertEquals("Error: La membresia con el id 99 no existe", exception.getMessage());
        verify(repo, times(1)).findById(99L);
    }
}