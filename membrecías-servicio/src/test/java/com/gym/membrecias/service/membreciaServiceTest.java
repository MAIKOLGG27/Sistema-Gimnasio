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

@ExtendWith(MockitoExtension.class) // Habilita el uso de Mockito
public class membreciaServiceTest {

    @Mock
    private membreciaRepository repo; // Simulamos el repositorio

    @InjectMocks
    private membreciaService service; // Inyectamos el mock en el servicio real

    private membreciaModel membresiaMock;

    @BeforeEach
    void setUp() {
        // Esto se ejecuta antes de cada prueba para preparar datos de prueba
        membresiaMock = new membreciaModel();
        membresiaMock.setId(1L);
        membresiaMock.setNombre("Membresía Premium");
        membresiaMock.setPrecio(50000.0);
        membresiaMock.setDuracionDias(30);
    }

    @Test
    void obtenerPorId_DebeRetornarMembresia_CuandoExiste() {
        // 1. Arrange (Preparar): Le decimos al mock qué hacer cuando lo llamen
        when(repo.findById(1L)).thenReturn(Optional.of(membresiaMock));

        // 2. Act (Actuar): Llamamos al método real de nuestro servicio
        membreciaModel resultado = service.obtenerPorId(1L);

        // 3. Assert (Comprobar): Verificamos que el resultado es el esperado
        assertNotNull(resultado);
        assertEquals("Membresía Premium", resultado.getNombre());
        verify(repo, times(1)).findById(1L); // Verificamos que el repositorio fue llamado exactamente 1 vez
    }

    @Test
    void obtenerPorId_DebeLanzarExcepcion_CuandoNoExiste() {
        // 1. Arrange: Simulamos que la base de datos no encuentra nada (Optional.empty)
        when(repo.findById(99L)).thenReturn(Optional.empty());

        // 2 & 3. Act & Assert: Comprobamos que lance tu RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.obtenerPorId(99L);
        });

        assertEquals("Error: La membresia con el id 99 no existe", exception.getMessage());
        verify(repo, times(1)).findById(99L);
    }
}