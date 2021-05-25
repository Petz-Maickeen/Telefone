package br.com.petz.phone.rest;

import br.com.petz.phone.domain.port.PhonePort;
import br.com.petz.phone.rest.model.ClientPhone;
import br.com.petz.phone.rest.model.DataResponse;
import br.com.petz.phone.rest.model.PhoneEmpty;
import br.com.petz.phone.rest.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cliente/{id_cliente}/phone")
public class PhoneController {
    @Autowired
    private PhonePort phonePort;

    @GetMapping
    private ResponseEntity<?> findClient(@Valid @PathVariable(value = "id_cliente") UUID idClient){
        List<ClientPhone> result = phonePort.findPhone(idClient);
        return result.isEmpty() ?
                ResponseEntity.status(404).body(DataResponse.builder().data(Constantes.TELEFONE_NAO_ENCONTRADO).build()):
                ResponseEntity.ok().body(DataResponse.builder().data(result).build());
    }

    @PatchMapping
    private ResponseEntity<?> updateClient(@Valid @PathVariable(value = "id_cliente")UUID idClient,
                                           @Valid @RequestBody ClientPhone clientPhone){
        clientPhone.setIdClient(idClient);
        PhoneEmpty phoneEmpty = phonePort.updatePhone(clientPhone);
        return phoneEmpty instanceof ClientPhone ?
                ResponseEntity.ok().body(DataResponse.builder().data(phoneEmpty).build()) :
                ResponseEntity.status(404).body(DataResponse.builder().data(Constantes.TELEFONE_NAO_ENCONTRADO).build());
    }

    @PostMapping
    private ResponseEntity<?> insertClient(@Valid @PathVariable(value = "id_cliente")UUID idClient,
                                           @Valid @RequestBody ClientPhone clientPhone){
        clientPhone.setIdClient(idClient);
        PhoneEmpty phoneEmpty = phonePort.insertPhone(clientPhone);
        return phoneEmpty instanceof ClientPhone ?
                ResponseEntity.status(201).body(DataResponse.builder().data(phoneEmpty).build()):
                ResponseEntity.status(400).body(DataResponse.builder().data(Constantes.TELEFONE_JA_CADASTRADO).build());
    }

    @DeleteMapping("/{telefone}")
    private ResponseEntity<?> removeClient(@Valid @PathVariable(value = "id_cliente")UUID idClient,
                                           @Valid @PathVariable(value = "telefone") String telefone){
        return phonePort.deletePhoneByClientIdAndPhone(idClient, telefone) ?
                ResponseEntity.ok().body(DataResponse.builder().data(Constantes.TELEFONE_EXCLUIDO_SUCESSO).build()):
                ResponseEntity.status(404).body(DataResponse.builder().data(Constantes.TELEFONE_NAO_ENCONTRADO).build());
    }

}
