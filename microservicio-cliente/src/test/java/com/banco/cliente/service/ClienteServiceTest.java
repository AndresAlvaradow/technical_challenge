package com.banco.cliente.service;

import com.banco.cliente.dto.ClienteDto;
import com.banco.cliente.entity.Cliente;
import com.banco.cliente.repository.ClienteRepository;
import com.banco.cliente.testdata.ClienteTestDataBuilder;
import com.banco.cliente.testdata.TestConstants;
import com.banco.cliente.usecase.ActualizarClienteUseCaseInterface;
import com.banco.cliente.usecase.CrearClienteUseCaseInterface;
import com.banco.cliente.usecase.EliminarClienteUseCaseInterface;
import com.banco.cliente.usecase.ObtenerClienteUseCaseInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CrearClienteUseCaseInterface crearClienteUseCase;

    @Mock
    private ObtenerClienteUseCaseInterface obtenerClienteUseCase;

    @Mock
    private ActualizarClienteUseCaseInterface actualizarClienteUseCase;

    @Mock
    private EliminarClienteUseCaseInterface eliminarClienteUseCase;

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService(clienteRepository, crearClienteUseCase, 
                obtenerClienteUseCase, actualizarClienteUseCase, eliminarClienteUseCase);
    }

    @Test
    void crearCliente_DeberiaUsarUseCase() {
        ClienteDto clienteDto = ClienteTestDataBuilder.joseLema().buildDto();
        
        ClienteDto clienteResponse = ClienteTestDataBuilder.joseLema()
                .withClienteId(TestConstants.DEFAULT_CLIENTE_ID)
                .buildDto();

        when(crearClienteUseCase.ejecutar(any(ClienteDto.class))).thenReturn(clienteResponse);

        ClienteDto resultado = clienteService.crearCliente(clienteDto);

        assertNotNull(resultado);
        assertEquals(TestConstants.DEFAULT_CLIENTE_ID, resultado.getClienteId());
        assertEquals("José Lema", resultado.getNombre());

        verify(crearClienteUseCase).ejecutar(clienteDto);
    }

    @Test
    void obtenerClientePorId_DeberiaUsarUseCase() {
        Long clienteId = TestConstants.DEFAULT_CLIENTE_ID;
        ClienteDto clienteResponse = ClienteTestDataBuilder.joseLema()
                .withClienteId(clienteId)
                .buildDto();

        when(obtenerClienteUseCase.ejecutarPorId(clienteId)).thenReturn(clienteResponse);

        ClienteDto resultado = clienteService.obtenerClientePorId(clienteId);

        assertNotNull(resultado);
        assertEquals(clienteId, resultado.getClienteId());
        assertEquals("José Lema", resultado.getNombre());

        verify(obtenerClienteUseCase).ejecutarPorId(clienteId);
    }

    @Test
    void obtenerClientePorIdentificacion_DeberiaUsarUseCase() {
        String identificacion = TestConstants.DEFAULT_IDENTIFICACION;
        ClienteDto clienteResponse = ClienteTestDataBuilder.joseLema()
                .withIdentificacion(identificacion)
                .buildDto();

        when(obtenerClienteUseCase.ejecutarPorIdentificacion(identificacion)).thenReturn(clienteResponse);

        ClienteDto resultado = clienteService.obtenerClientePorIdentificacion(identificacion);

        assertNotNull(resultado);
        assertEquals(identificacion, resultado.getIdentificacion());
        assertEquals("José Lema", resultado.getNombre());

        verify(obtenerClienteUseCase).ejecutarPorIdentificacion(identificacion);
    }

    @Test
    void actualizarCliente_DeberiaUsarUseCase() {
        Long clienteId = TestConstants.DEFAULT_CLIENTE_ID;
        ClienteDto clienteDto = ClienteTestDataBuilder.aValidCliente()
                .withNombre(TestConstants.CLIENTE_NOMBRE_ACTUALIZADO)
                .buildDto();

        ClienteDto clienteResponse = ClienteTestDataBuilder.aValidCliente()
                .withClienteId(clienteId)
                .withNombre(TestConstants.CLIENTE_NOMBRE_ACTUALIZADO)
                .buildDto();

        when(actualizarClienteUseCase.ejecutar(clienteId, clienteDto)).thenReturn(clienteResponse);

        ClienteDto resultado = clienteService.actualizarCliente(clienteId, clienteDto);

        assertNotNull(resultado);
        assertEquals(clienteId, resultado.getClienteId());
        assertEquals(TestConstants.CLIENTE_NOMBRE_ACTUALIZADO, resultado.getNombre());

        verify(actualizarClienteUseCase).ejecutar(clienteId, clienteDto);
    }

    @Test
    void eliminarCliente_DeberiaUsarUseCase() {
        Long clienteId = TestConstants.DEFAULT_CLIENTE_ID;

        clienteService.eliminarCliente(clienteId);

        verify(eliminarClienteUseCase).ejecutar(clienteId);
    }

    @Test
    void obtenerTodosLosClientes_DeberiaRetornarLista() {
        Cliente cliente1 = ClienteTestDataBuilder.joseLema().buildEntity();
        Cliente cliente2 = ClienteTestDataBuilder.marianela()
                .withClienteId(TestConstants.SECOND_CLIENTE_ID)
                .buildEntity();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<ClienteDto> resultado = clienteService.obtenerTodosLosClientes();

        assertNotNull(resultado);
        assertEquals(TestConstants.EXPECTED_LIST_SIZE_TWO, resultado.size());
        assertEquals("José Lema", resultado.get(TestConstants.FIRST_ELEMENT_INDEX).getNombre());
        assertEquals("Marianela Montalvo", resultado.get(TestConstants.SECOND_ELEMENT_INDEX).getNombre());

        verify(clienteRepository).findAll();
    }
}