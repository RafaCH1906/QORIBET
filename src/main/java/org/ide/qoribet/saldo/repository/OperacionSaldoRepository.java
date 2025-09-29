package org.ide.qoribet.saldo.repository;

import org.ide.qoribet.saldo.entity.OperacionSaldo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OperacionSaldoRepository extends JpaRepository<OperacionSaldo, Long> {
    List<OperacionSaldo> findBySaldoDigitalId(Long saldoDigitalId);
}

