package br.com.petz.phone.domain.service;

import br.com.petz.phone.domain.adapter.PhoneRepository;
import br.com.petz.phone.domain.port.PhonePort;
import br.com.petz.phone.integration.entity.PhoneEntity;
import br.com.petz.phone.rest.model.ClientPhone;
import br.com.petz.phone.rest.model.PhoneEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PhoneService implements PhonePort {
    @Autowired
    private PhoneRepository phoneRepository;
    @Override
    public List<ClientPhone> findPhone(UUID idClient) {
        List<ClientPhone> result = new ArrayList<>();
        if(phoneRepository.existsByIdCliente(idClient)){
            List<PhoneEntity> aux = phoneRepository.findAllByIdCliente(idClient);
            for(PhoneEntity entity: aux){
                result.add(PhoneMapper.entityToModel(entity));
            }
        }
        return result;
    }

    @Override
    public PhoneEmpty insertPhone(ClientPhone clientPhone) {
        try {
            if (phoneRepository.existsByIdClienteAndTelefone(clientPhone.getIdClient(), clientPhone.getTelefone())) {
                return new PhoneEmpty();
            } else {
                return PhoneMapper.entityToModel(phoneRepository.save(PhoneMapper.modelToEntity(clientPhone)));
            }
        } catch (NullPointerException ex){
            return new PhoneEmpty();
        }
    }

    @Override
    public PhoneEmpty updatePhone(ClientPhone clientPhone) {
        try {
            if (phoneRepository.existsByIdClienteAndTelefone(clientPhone.getIdClient(), clientPhone.getTelefone())) {
                return PhoneMapper.entityToModel(phoneRepository.save(PhoneMapper.modelToEntity(clientPhone)));
            } else {
                return new PhoneEmpty();
            }
        } catch (NullPointerException ex){
            return new PhoneEmpty();
        }
    }

    @Override
    public boolean deletePhoneByClientIdAndPhone(UUID idCliente, String telefone) {
        if(phoneRepository.existsByIdClienteAndTelefone(idCliente,telefone)){
            phoneRepository.delete(phoneRepository.findByIdClienteAndTelefone(idCliente,telefone));
            return true;
        } else {
            return false;
        }
    }
}
