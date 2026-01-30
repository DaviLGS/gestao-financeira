package financeira.gestao.demo.service.transacao.transacaoService;

import financeira.gestao.demo.domain.transacao.Transacao;
import financeira.gestao.demo.application.dto.request.TransacaoRequest;
import financeira.gestao.demo.application.dto.response.TransacaoResponse;
import financeira.gestao.demo.repository.TransacaoRepository;
import financeira.gestao.demo.service.cambio.CambioService;
import financeira.gestao.demo.service.transacao.operacoes.OperacaoFinanceira;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final Map<String, OperacaoFinanceira> operacoes;
    private final TransacaoRepository transacaoRepository;
    private final CambioService cambioService;

    public TransacaoServiceImpl(
            List<OperacaoFinanceira> operacoes,
            TransacaoRepository transacaoRepository,
            CambioService cambioService
    ) {

        this.operacoes = operacoes.stream()
                .collect(Collectors.toMap(
                        OperacaoFinanceira::getCodigo,
                        Function.identity()
                ));

        this.transacaoRepository = transacaoRepository;
        this.cambioService = cambioService;
    }

    @Override
    public TransacaoResponse executar(TransacaoRequest request, Long userId) {

        OperacaoFinanceira operacao = operacoes.get(request.getCodigo());

        if (operacao == null) {
            throw new RuntimeException("Tipo de transação não suportado!");
        }

        BigDecimal taxa = BigDecimal.ONE;

        if (request.getMoeda() != null && !request.getMoeda().equalsIgnoreCase("BRL")) {
            taxa = cambioService.getTaxaCambio(request.getMoeda());
            BigDecimal valorConvertido = request.getValor().multiply(taxa);
            request.setValor(valorConvertido);
        }

        TransacaoResponse response = operacao.registrar(request, userId);

        response.setTaxaCambio(taxa);
        response.setMoeda(request.getMoeda());

        return response;
    }

    @Override
    public List<TransacaoResponse> listarPorPeriodo(
            Long userId,
            LocalDateTime inicio,
            LocalDateTime fim
    ) {
        List<Transacao> transacoes =
                transacaoRepository.findByUsuarioIdAndCriadoAsBetween(userId, inicio, fim);

        return transacoes.stream()
                .map(TransacaoResponse::new)
                .toList();
    }
}
