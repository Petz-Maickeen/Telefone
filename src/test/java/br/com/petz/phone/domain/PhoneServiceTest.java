package br.com.petz.phone.domain;

import br.com.petz.phone.domain.adapter.PhoneRepository;
import br.com.petz.phone.domain.service.PhoneMapper;
import br.com.petz.phone.domain.service.PhoneService;
import br.com.petz.phone.integration.entity.PhoneEntity;
import br.com.petz.phone.rest.model.ClientPhone;
import br.com.petz.phone.rest.model.PhoneEmpty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PhoneServiceTest {

    @Autowired
    private PhoneService phoneService;

    @MockBean
    private PhoneRepository phoneRepository;

    @Test
    public void shouldFindPhoneByClientID(){

        UUID idCliente = UUID.randomUUID();
        List<ClientPhone> listaPhone = new ArrayList<>();
        List<PhoneEntity> listaEntity = new ArrayList<>();
        ClientPhone cliente = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddi("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        listaPhone.add(cliente);
        listaEntity.add(PhoneMapper.modelToEntity(cliente));
        when(phoneRepository.existsByIdCliente(idCliente)).thenReturn(true);
        when(phoneRepository.findAllByIdCliente(idCliente)).thenReturn(listaEntity);

        assertEquals(phoneService.findPhone(idCliente), listaPhone);
    }

    @Test
    public void shouldNotFindPhoneByClientID(){

        UUID idCliente = UUID.randomUUID();
        List<ClientPhone> listaPhone = new ArrayList<>();
        List<PhoneEntity> listaEntity = new ArrayList<>();
        ClientPhone cliente = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddi("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        listaPhone.add(cliente);
        listaEntity.add(PhoneMapper.modelToEntity(cliente));
        when(phoneRepository.existsByIdCliente(idCliente)).thenReturn(false);
        when(phoneRepository.findAllByIdCliente(idCliente)).thenReturn(listaEntity);

        assertTrue(phoneService.findPhone(idCliente).isEmpty());
    }

    @Test
    public void shouldInserClient(){
        UUID idCliente = UUID.randomUUID();
        ClientPhone clienteE = ClientPhone.builder()
                .ddi("+55")
                .ddi("011")
                .telefone("963269290")
                .principal("principal")
                .build();
        ClientPhone clienteS = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddi("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        when(phoneRepository.existsByIdCliente(idCliente)).thenReturn(false);
        when(phoneRepository.save(PhoneMapper.modelToEntity(clienteE))).thenReturn(PhoneMapper.modelToEntity(clienteS));

        assertEquals(phoneService.insertPhone(clienteE),clienteS);
    }

    @Test
    public void shouldNotInserExistingPhone(){
        UUID idCliente = UUID.randomUUID();
        ClientPhone clienteE = ClientPhone.builder()
                .ddi("+55")
                .ddi("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        when(phoneRepository.existsByIdCliente(idCliente)).thenReturn(true);
        assertTrue(phoneService.insertPhone(clienteE) instanceof PhoneEmpty);
    }

    @Test
    public void shouldUpdateClient(){
        UUID idCliente = UUID.randomUUID();
        ClientPhone clienteE = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddd("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        when(phoneRepository.existsByIdClienteAndTelefone(idCliente,clienteE.getTelefone())).thenReturn(true);
        when(phoneRepository.save(PhoneMapper.modelToEntity(clienteE))).thenReturn(PhoneMapper.modelToEntity(clienteE));

        assertEquals(phoneService.updatePhone(clienteE),clienteE);
    }

    @Test
    public void shouldNotUpdateInvalidPhone(){
        UUID idCliente = UUID.randomUUID();
        ClientPhone clienteE = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddd("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        when(phoneRepository.existsByIdClienteAndTelefone(idCliente,clienteE.getTelefone())).thenReturn(false);

        assertTrue(phoneService.updatePhone(clienteE) instanceof PhoneEmpty);
    }

    @Test
    public void shouldDeleteClient(){
        UUID idCliente = UUID.randomUUID();
        String telefone = "963269290";

        when(phoneRepository.existsByIdClienteAndTelefone(idCliente,telefone)).thenReturn(true);

        assertTrue(phoneService.deletePhoneByClientIdAndPhone(idCliente, telefone));
    }

    @Test
    public void shouldNotDeleteInvalidClient(){
        UUID idCliente = UUID.randomUUID();
        String telefone = "963269290";

        when(phoneRepository.existsByIdClienteAndTelefone(idCliente,telefone)).thenReturn(false);

        assertFalse(phoneService.deletePhoneByClientIdAndPhone(idCliente,telefone));
    }
}
