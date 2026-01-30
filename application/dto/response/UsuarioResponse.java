package financeira.gestao.demo.application.dto.response;

import financeira.gestao.demo.domain.entities.usuario.Role;
import financeira.gestao.demo.domain.entities.usuario.Usuario;

public class UsuarioResponse {

    private Long id;
    private String email;
    private Role role;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.role = usuario.getRole();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
