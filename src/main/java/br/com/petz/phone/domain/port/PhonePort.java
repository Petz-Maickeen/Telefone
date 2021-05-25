package br.com.petz.phone.domain.port;

import br.com.petz.phone.rest.model.ClientPhone;
import br.com.petz.phone.rest.model.PhoneEmpty;

import java.util.List;
import java.util.UUID;

public interface PhonePort {
    List<ClientPhone> findPhone(UUID idClient);
    PhoneEmpty insertPhone(ClientPhone clientePhone);
    PhoneEmpty updatePhone(ClientPhone clientePhone);
    boolean deletePhoneByClientIdAndPhone(UUID idCLiente, String telefone);
}
