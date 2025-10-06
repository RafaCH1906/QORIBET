package org.ide.qoribet.partido.repository;

import org.ide.qoribet.partido.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByLigaId(Long ligaId);
    List<Partido> findByEquipoLocalIdOrEquipoVisitanteId(Long equipoLocalId, Long equipoVisitanteId);
}
