package financeira.gestao.demo.service.transacao.operacoes;

import financeira.gestao.demo.domain.transacao.StatusTransacao;
import financeira.gestao.demo.domain.transacao.TipoTransacao;
import financeira.gestao.demo.domain.transacao.Transacao;
import financeira.gestao.demo.domain.usuario.Usuario;
import financeira.gestao.demo.application.dto.request.TransacaoRequest;
import financeira.gestao.demo.application.dto.response.SaldoResponse;
import financeira.gestao.demo.application.dto.response.TransacaoResponse;
import financeira.gestao.demo.repository.TransacaoRepository;
import financeira.gestao.demo.repository.UsuarioRepository;
import financeira.gestao.demo.service.saldo.SaldoBancarioService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DepositoService implements OperacaoFinanceira {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SaldoBancarioService saldoBancarioService;

    public DepositoService(
            TransacaoRepository transacaoRepository,
            UsuarioRepository usuarioRepository,
            SaldoBancarioService saldoBancarioService
    ) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.saldoBancarioService = saldoBancarioService;
    }

    @Override
    public TipoTransacao getTipo() {
        return TipoTransacao.ENTRADA;
    }

    @Override
    public TransacaoResponse registrar(TransacaoRequest request, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor inválido");
        }

        SaldoResponse saldo = null;

        if (request.isAtualizarSaldo()) {
            saldo = saldoBancarioService.buscarSaldo(usuarioId);
            BigDecimal novoSaldo = saldo.getSaldo().add(request.getValor());
            saldo.setSaldo(novoSaldo);
            saldoBancarioService.atualizarSaldo(saldo);
        }

        Transacao transacao = new Transacao();
        transacao.setDescricao(request.getDescricao());
        transacao.setValor(request.getValor());
        transacao.setTipo(TipoTransacao.ENTRADA);
        transacao.setStatus(StatusTransacao.APROVADO);
        transacao.setUsuario(usuario);
        transacao.setCriadoAs(LocalDateTime.now());
        transacao.setMoeda(request.getMoeda() != null ? request.getMoeda() : "BRL");

        Transacao salva = transacaoRepository.save(transacao);

        return new TransacaoResponse(salva);
    }

    public String getCodigo() {
        return "DEPOSITO";
    }
}
