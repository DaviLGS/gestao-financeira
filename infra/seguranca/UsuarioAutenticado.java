package financeira.gestao.demo.infra.seguranca;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioAutenticado {

    public static Long getId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsuarioDetalhes detalhes = (UsuarioDetalhes) auth.getPrincipal();
        return detalhes.getId();
    }
}
