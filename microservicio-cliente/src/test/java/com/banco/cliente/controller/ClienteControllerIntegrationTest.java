package com.banco.cliente.controller;

import com.banco.cliente.dto.ClienteDto;
import com.banco.cliente.testdata.ClienteTestDataBuilder;
import com.banco.cliente.testdata.TestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearCliente_DatosValidos_DeberiaRetornar201() throws Exception {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Test User");
        clienteDto.setGenero(TestConstants.DEFAULT_GENERO_MASCULINO);
        clienteDto.setEdad(TestConstants.DEFAULT_EDAD_VALIDA);
        clienteDto.setIdentificacion("12345678");
        clienteDto.setDireccion(TestConstants.DEFAULT_DIRECCION);
        clienteDto.setTelefono(TestConstants.DEFAULT_TELEFONO);
        clienteDto.setContrasena("TestPassword123");
        clienteDto.setEstado(TestConstants.DEFAULT_ESTADO_ACTIVO);

        mockMvc.perform(post(TestConstants.CLIENTES_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Test User"))
                .andExpect(jsonPath("$.estado").value(TestConstants.DEFAULT_ESTADO_ACTIVO));
    }

    @Test 
    void crearCliente_DatosInvalidos_DeberiaRetornar400() throws Exception {
        ClienteDto clienteDto = new ClienteDto();
        
        mockMvc.perform(post(TestConstants.CLIENTES_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerClientes_DeberiaRetornar200() throws Exception {
        mockMvc.perform(get(TestConstants.CLIENTES_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerClienteInexistente_DeberiaRetornar404() throws Exception {
        mockMvc.perform(get("/clientes/" + TestConstants.NONEXISTENT_CLIENTE_ID))
                .andExpect(status().isNotFound());
    }
}