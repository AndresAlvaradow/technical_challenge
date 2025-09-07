package com.banco.cliente.service;

import com.banco.cliente.config.RabbitMQConfig;
import com.banco.cliente.dto.ClienteEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ClienteEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ClienteEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarClienteCreado(Long clienteId, String nombre, String identificacion) {
        ClienteEventDto evento = new ClienteEventDto(clienteId, nombre, identificacion, "CLIENTE_CREATED");
        
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLIENTE_EXCHANGE,
                    RabbitMQConfig.CLIENTE_ROUTING_KEY,
                    evento
            );
            logger.info("Evento de cliente creado publicado: {}", clienteId);
        } catch (Exception e) {
            logger.error("Error al publicar evento de cliente creado: {}", e.getMessage());
        }
    }

    public void publicarClienteActualizado(Long clienteId, String nombre, String identificacion) {
        ClienteEventDto evento = new ClienteEventDto(clienteId, nombre, identificacion, "CLIENTE_UPDATED");
        
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLIENTE_EXCHANGE,
                    RabbitMQConfig.CLIENTE_ROUTING_KEY,
                    evento
            );
            logger.info("Evento de cliente actualizado publicado: {}", clienteId);
        } catch (Exception e) {
            logger.error("Error al publicar evento de cliente actualizado: {}", e.getMessage());
        }
    }

    public void publicarClienteEliminado(Long clienteId, String nombre, String identificacion) {
        ClienteEventDto evento = new ClienteEventDto(clienteId, nombre, identificacion, "CLIENTE_DELETED");
        
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CLIENTE_EXCHANGE,
                    RabbitMQConfig.CLIENTE_ROUTING_KEY,
                    evento
            );
            logger.info("Evento de cliente eliminado publicado: {}", clienteId);
        } catch (Exception e) {
            logger.error("Error al publicar evento de cliente eliminado: {}", e.getMessage());
        }
    }
}