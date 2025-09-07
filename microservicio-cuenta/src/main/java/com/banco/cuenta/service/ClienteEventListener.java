package com.banco.cuenta.service;

import com.banco.cuenta.dto.ClienteEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ClienteEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ClienteEventListener.class);

    @RabbitListener(queues = "cliente.queue")
    public void handleClienteEvent(ClienteEventDto evento) {
        logger.info("Evento de cliente recibido: {} - Cliente ID: {}", 
                evento.getEventType(), evento.getClienteId());

        switch (evento.getEventType()) {
            case "CLIENTE_CREATED":
                handleClienteCreated(evento);
                break;
            case "CLIENTE_UPDATED":
                handleClienteUpdated(evento);
                break;
            case "CLIENTE_DELETED":
                handleClienteDeleted(evento);
                break;
            default:
                logger.warn("Tipo de evento desconocido: {}", evento.getEventType());
        }
    }

    private void handleClienteCreated(ClienteEventDto evento) {
        logger.info("Procesando cliente creado: {} - {}", evento.getClienteId(), evento.getNombre());
    }

    private void handleClienteUpdated(ClienteEventDto evento) {
        logger.info("Procesando cliente actualizado: {} - {}", evento.getClienteId(), evento.getNombre());
    }

    private void handleClienteDeleted(ClienteEventDto evento) {
        logger.info("Procesando cliente eliminado: {} - {}", evento.getClienteId(), evento.getNombre());
    }
}