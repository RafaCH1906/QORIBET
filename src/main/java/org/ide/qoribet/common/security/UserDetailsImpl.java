package org.ide.qoribet.common.security;

// Implementación de UserDetails para adaptar la entidad Usuario a Spring Security
import org.ide.qoribet.usuario.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    // Referencia a la entidad Usuario
    private final Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    // Devuelve los roles/autoridades del usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().name()));
    }

    // Devuelve el hash de la contraseña
    @Override
    public String getPassword() {
        return usuario.getPassword_hash();
    }

    // Devuelve el nombre de usuario
    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    // Indica si la cuenta está expirada
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indica si la cuenta está bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return !usuario.getEstado().name().equals("BANEADO");
    }

    // Indica si las credenciales están expiradas
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indica si el usuario está habilitado
    @Override
    public boolean isEnabled() {
        return usuario.getEstado().name().equals("ACTIVO");
    }

    // Devuelve la entidad Usuario original
    public Usuario getUsuario() {
        return usuario;
    }
}
