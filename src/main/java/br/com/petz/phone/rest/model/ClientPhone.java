package br.com.petz.phone.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientPhone extends PhoneEmpty{

    @JsonProperty(value = "idClient")
    private UUID idClient;

    @JsonProperty(value = "ddi")
    private String ddi;

    @JsonProperty(value = "ddd")
    private String ddd;

    @JsonProperty(value = "telefone")
    private String telefone;

    @JsonProperty(value = "principal")
    private String principal;

}
