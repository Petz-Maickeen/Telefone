package br.com.petz.phone.rest;

import br.com.petz.phone.JsonUtil;
import br.com.petz.phone.domain.port.PhonePort;
import br.com.petz.phone.rest.model.ClientPhone;
import br.com.petz.phone.rest.model.DataResponse;
import br.com.petz.phone.rest.model.PhoneEmpty;
import br.com.petz.phone.rest.util.Constantes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(PhoneController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhonePort phonePort;

    @Test
    public void shouldFindClientByID() throws Exception {
        UUID idCliente = UUID.randomUUID();
        List<ClientPhone> lista = new ArrayList<>();
        ClientPhone cliente = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddd("011")
                .telefone("963269290")
                .principal("principal")
                .build();
        lista.add(cliente);

        when(phonePort.findPhone(idCliente)).thenReturn(lista);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/cliente/" + idCliente + "/phone"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(lista).build())));
    }

    @Test
    public void shouldNotFindClientByID() throws Exception {
        UUID idCliente = UUID.randomUUID();
        List<ClientPhone> lista = new ArrayList<>();

        when(phonePort.findPhone(idCliente)).thenReturn(lista);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/cliente/" + idCliente + "/phone"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.TELEFONE_NAO_ENCONTRADO).build())));
    }

    @Test
    public void shouldInsertClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        ClientPhone clienteE = ClientPhone.builder()
                .ddi("+55")
                .ddd("011")
                .telefone("963269290")
                .principal("principal")
                .build();
        ClientPhone clienteS = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddd("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        when(phonePort.insertPhone(clienteS)).thenReturn(clienteS);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/cliente/" + idCliente + "/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(clienteE)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(clienteS).build())));
    }

    @Test
    public void shouldNotInsertClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        ClientPhone clienteE = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddd("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        when(phonePort.insertPhone(clienteE)).thenReturn(new PhoneEmpty());
        mockMvc
                .perform(MockMvcRequestBuilders.post("/cliente/" + idCliente + "/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(clienteE)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.TELEFONE_JA_CADASTRADO).build())));
    }

    @Test
    public void shouldUpdateClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        ClientPhone clienteE = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddd("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        when(phonePort.updatePhone(clienteE)).thenReturn(clienteE);
        mockMvc
                .perform(MockMvcRequestBuilders.patch("/cliente/"+idCliente+"/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(clienteE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(clienteE).build())));
    }

    @Test
    public void shouldNotUpdateClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        ClientPhone clienteE = ClientPhone.builder()
                .idClient(idCliente)
                .ddi("+55")
                .ddd("011")
                .telefone("963269290")
                .principal("principal")
                .build();

        when(phonePort.updatePhone(clienteE)).thenReturn(new PhoneEmpty());
        mockMvc
                .perform(MockMvcRequestBuilders.patch("/cliente/"+idCliente+"/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(clienteE)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.TELEFONE_NAO_ENCONTRADO).build())));
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        String telefone = "963269290";
        when(phonePort.deletePhoneByClientIdAndPhone(idCliente,telefone)).thenReturn(true);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/cliente/" + idCliente + "/phone/" + telefone))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.TELEFONE_EXCLUIDO_SUCESSO).build())));
    }

    @Test
    public void shouldNotDeleteClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        String telefone = "963269290";
        when(phonePort.deletePhoneByClientIdAndPhone(idCliente,telefone)).thenReturn(false);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/cliente/" + idCliente + "/phone/" + telefone))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.TELEFONE_NAO_ENCONTRADO).build())));
    }
}