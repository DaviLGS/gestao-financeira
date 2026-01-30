package financeira.gestao.demo.infra.service.transacao.operacoes;

import financeira.gestao.demo.domain.entities.transacao.StatusTransacao;
import financeira.gestao.demo.domain.entities.transacao.TipoTransacao;
import financeira.gestao.demo.domain.entities.transacao.Transacao;
import financeira.gestao.demo.domain.entities.usuario.Usuario;
import financeira.gestao.demo.application.dto.request.TransacaoRequest;
import financeira.gestao.demo.application.dto.response.SaldoResponse;
import financeira.gestao.demo.application.dto.response.TransacaoResponse;
import financeira.gestao.demo.infra.service.saldo.SaldoBancarioService;
import financeira.gestao.demo.domain.repository.TransacaoRepository;
import financeira.gestao.demo.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class SaqueService implements OperacaoFinanceira {

    private final UsuarioRepository usuarioRepository;
    private final TransacaoRepository transacaoRepository;
    private final SaldoBancarioService saldoBancarioService;

    public SaqueService(UsuarioRepository usuarioRepository,
                        TransacaoRepository transacaoRepository,
                        SaldoBancarioService saldoBancarioService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.transacaoRepository = transacaoRepository;
        this.saldoBancarioService = saldoBancarioService;
    }

    @Override
    public TipoTransacao getTipo() {
        return TipoTransacao.SAIDA;
    }

    @Override
    @Transactional
    public TransacaoResponse registrar(TransacaoRequest request, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor do saque deve ser maior que zero");
        }

        if (request.isAtualizarSaldo()) {
            SaldoResponse saldo = saldoBancarioService.buscarSaldo(usuarioId);
            BigDecimal saldoDisponivel = saldo.getSaldo().add(saldo.getLimite());

            if (request.getValor().compareTo(saldoDisponivel) > 0) {
                throw new RuntimeException("Saldo insuficiente");
            }

            saldo.setSaldo(saldo.getSaldo().subtract(request.getValor()));
            saldoBancarioService.atualizarSaldo(saldo);
        }

        Transacao transacao = new Transacao();
        transacao.setDescricao("Saque realizado");
        transacao.setTipo(TipoTransacao.SAIDA);
        transacao.setStatus(StatusTransacao.APROVADO);
        transacao.setValor(request.getValor());
        transacao.setUsuario(usuario);
        transacao.setCriadoAs(LocalDateTime.now());
        transacao.setMoeda(request.getMoeda() != null ? request.getMoeda() : "BRL");

        Transacao salva = transacaoRepository.save(transacao);

        return new TransacaoResponse(salva);
    }

    @Override
    public String getCodigo() {
        return "SAQUE";
    }
}
