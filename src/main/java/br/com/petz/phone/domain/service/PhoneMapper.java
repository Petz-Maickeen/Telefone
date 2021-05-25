package br.com.petz.phone.domain.service;

import br.com.petz.phone.integration.entity.PhoneEntity;
import br.com.petz.phone.rest.model.ClientPhone;

public class PhoneMapper {
    public static PhoneEntity modelToEntity(ClientPhone clientPhone){
        return PhoneEntity.builder()
                .idCliente(clientPhone.getIdClient())
                .ddi(clientPhone.getDdi())
                .ddd(clientPhone.getDdd())
                .telefone(clientPhone.getTelefone())
                .principal(clientPhone.getPrincipal())
                .build();
    }
    public static ClientPhone entityToModel(PhoneEntity entity){
        return ClientPhone.builder()
                .idClient(entity.getIdCliente())
                .ddi(entity.getDdi())
                .ddd(entity.getDdd())
                .telefone(entity.getTelefone())
                .principal(entity.getPrincipal())
                .build();
    }
}
