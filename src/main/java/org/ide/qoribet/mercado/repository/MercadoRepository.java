package org.ide.qoribet.mercado.repository;

import org.ide.qoribet.mercado.entity.Mercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercadoRepository extends JpaRepository<Mercado, Long> {
}
