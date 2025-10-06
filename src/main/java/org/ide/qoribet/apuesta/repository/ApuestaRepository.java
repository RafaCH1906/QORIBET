package org.ide.qoribet.apuesta.repository;

import org.ide.qoribet.apuesta.entity.Apuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApuestaRepository extends JpaRepository<Apuesta, Long> {
    List<Apuesta> findByUsuarioId(Long usuarioId);
    List<Apuesta> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}
