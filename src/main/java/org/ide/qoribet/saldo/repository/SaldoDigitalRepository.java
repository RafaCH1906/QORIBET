package org.ide.qoribet.saldo.repository;

import org.ide.qoribet.saldo.entity.SaldoDigital;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SaldoDigitalRepository extends JpaRepository<SaldoDigital, Long> {
    Optional<SaldoDigital> findByUsuarioId(Long usuarioId);
}

