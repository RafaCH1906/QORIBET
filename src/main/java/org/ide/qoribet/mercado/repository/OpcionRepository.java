package org.ide.qoribet.mercado.repository;

import org.ide.qoribet.mercado.entity.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Long> {
}
