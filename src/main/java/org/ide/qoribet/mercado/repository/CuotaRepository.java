package org.ide.qoribet.mercado.repository;

import org.ide.qoribet.mercado.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {
    List<Cuota> findByOpcionId(Long opcionId);
}
