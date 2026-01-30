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
public class CompraService implements OperacaoFinanceira {

    private final UsuarioRepository usuarioRepository;
    private final TransacaoRepository transacaoRepository;
    private final SaldoBancarioService saldoBancarioService;

    public CompraService(
            UsuarioRepository usuarioRepository,
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
    public TransacaoResponse registrar(TransacaoRequest request, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (request.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor inválido");
        }

        SaldoResponse saldoResponse = null;
        BigDecimal saldoDisponivel = null;

        if (request.isAtualizarSaldo()) {
            saldoResponse = saldoBancarioService.buscarSaldo(usuarioId);
            saldoDisponivel = saldoResponse.getSaldo().add(saldoResponse.getLimite());

            if (request.getValor().compareTo(saldoDisponivel) > 0) {
                throw new RuntimeException("Saldo insuficiente");
            }
        }

        Transacao transacao = new Transacao();
        transacao.setDescricao(request.getDescricao());
        transacao.setTipo(TipoTransacao.SAIDA);
        transacao.setValor(request.getValor());
        transacao.setCriadoAs(LocalDateTime.now());
        transacao.setUsuario(usuario);
        transacao.setStatus(StatusTransacao.APROVADO);
        transacao.setMoeda(request.getMoeda() != null ? request.getMoeda() : "BRL");

        Transacao salva = transacaoRepository.save(transacao);

        if (request.isAtualizarSaldo() && saldoResponse != null) {
            BigDecimal novoSaldo = saldoResponse.getSaldo().subtract(request.getValor());
            saldoResponse.setSaldo(novoSaldo);
            saldoBancarioService.atualizarSaldo(saldoResponse);
        }

        return new TransacaoResponse(salva);
    }

    @Override
    public String getCodigo() {
        return "COMPRA";
    }
}
