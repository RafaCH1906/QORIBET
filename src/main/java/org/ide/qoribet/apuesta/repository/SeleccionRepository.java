package org.ide.qoribet.apuesta.repository;

import org.ide.qoribet.apuesta.entity.Seleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeleccionRepository extends JpaRepository<Seleccion, Long> {
    List<Seleccion> findByApuestaId(Long apuestaId);
    List<Seleccion> findByOpcionId(Long opcionId);
}
