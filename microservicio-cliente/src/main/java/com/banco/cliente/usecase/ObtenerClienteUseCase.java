package com.banco.cliente.usecase;

import com.banco.cliente.dto.ClienteDto;
import com.banco.cliente.entity.Cliente;
import com.banco.cliente.exception.ClienteNotFoundException;
import com.banco.cliente.domain.repository.ClienteRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ObtenerClienteUseCase implements ObtenerClienteUseCaseInterface {

    private final ClienteRepositoryInterface clienteRepository;

    @Autowired
    public ObtenerClienteUseCase(ClienteRepositoryInterface clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteDto ejecutarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
        return convertirEntidadADto(cliente);
    }

    public ClienteDto ejecutarPorIdentificacion(String identificacion) {
        Cliente cliente = clienteRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con identificación: " + identificacion));
        return convertirEntidadADto(cliente);
    }

    private ClienteDto convertirEntidadADto(Cliente cliente) {
        ClienteDto dto = new ClienteDto();
        dto.setClienteId(cliente.getClienteId());
        dto.setNombre(cliente.getNombre());
        dto.setGenero(cliente.getGenero());
        dto.setEdad(cliente.getEdad());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setContrasena(cliente.getContrasena());
        dto.setEstado(cliente.getEstado());
        return dto;
    }
}