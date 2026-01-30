package financeira.gestao.demo.domain.repository;

import financeira.gestao.demo.domain.entities.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByUsuarioIdAndCriadoAsBetween(
            Long userId,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<Transacao> findByUsuarioId(Long userId);
}
