package financeira.gestao.demo.infra.service.autenticacao;

import financeira.gestao.demo.domain.entities.usuario.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "chave-super-secreta-demo-com-mais-de-32-caracteres";
    private static final long EXPIRACAO = 1000 * 60 * 60; // 1 hora

    public String gerarToken(Usuario usuario) {

        return Jwts.builder()
                .setSubject(usuario.getId().toString()) // ID do usuário
                .claim("role", usuario.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(getChaveAssinatura(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extrairUsuarioId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public boolean tokenValido(String token) {
        return !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getChaveAssinatura())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getChaveAssinatura() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }
}
