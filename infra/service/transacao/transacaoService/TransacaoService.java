package financeira.gestao.demo.infra.service.transacao.transacaoService;

import financeira.gestao.demo.application.dto.request.TransacaoRequest;
import financeira.gestao.demo.application.dto.response.TransacaoResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoService {

    TransacaoResponse executar(TransacaoRequest request, Long userId);

    List<TransacaoResponse> listarPorPeriodo(
            Long userId,
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
