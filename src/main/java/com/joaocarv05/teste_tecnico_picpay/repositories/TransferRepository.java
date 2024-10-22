package com.joaocarv05.teste_tecnico_picpay.repositories;

import com.joaocarv05.teste_tecnico_picpay.domain.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer,Long> {
}
