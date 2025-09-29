package org.ide.qoribet.promocion.repository;

import org.ide.qoribet.promocion.entity.PromocionUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PromocionUsuarioRepository extends JpaRepository<PromocionUsuario, Long> {
    List<PromocionUsuario> findByUsuarioId(Long usuarioId);
    List<PromocionUsuario> findByPromocionId(Long promocionId);
}
