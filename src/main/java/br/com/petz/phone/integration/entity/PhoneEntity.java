package br.com.petz.phone.integration.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "telefone")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PhonePK.class)
public class PhoneEntity {

    @Id
    @NotNull
    @Column(name = "id_cliente",columnDefinition = "BINARY(16)")
    private UUID idCliente;

    @Id
    @NotNull
    @Column(name = "telefone")
    private String telefone;

    @NotNull
    @Column(name = "ddi")
    private String ddi;

    @NotNull
    @Column(name = "ddd")
    private String ddd;

    @NotNull
    @Column(name = "principal")
    private String principal;
}
