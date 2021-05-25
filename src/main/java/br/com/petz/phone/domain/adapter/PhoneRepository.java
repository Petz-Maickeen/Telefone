package br.com.petz.phone.domain.adapter;

import br.com.petz.phone.integration.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhoneRepository extends JpaRepository<PhoneEntity, String> {
    boolean existsByIdClienteAndTelefone(UUID idClient, String telefone);
    boolean existsByIdCliente(UUID idClient);
    List<PhoneEntity> findAllByIdCliente(UUID idClient);
    PhoneEntity findByIdClienteAndTelefone(UUID idClient, String telefone);
}
