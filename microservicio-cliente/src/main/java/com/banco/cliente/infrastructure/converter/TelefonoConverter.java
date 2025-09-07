package com.banco.cliente.infrastructure.converter;

import com.banco.cliente.domain.valueobject.Telefono;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TelefonoConverter implements AttributeConverter<Telefono, String> {

    @Override
    public String convertToDatabaseColumn(Telefono telefono) {
        return telefono != null ? telefono.getNumero() : null;
    }

    @Override
    public Telefono convertToEntityAttribute(String numero) {
        return numero != null ? new Telefono(numero) : null;
    }
}