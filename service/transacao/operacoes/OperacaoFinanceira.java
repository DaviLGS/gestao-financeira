package financeira.gestao.demo.service.transacao.operacoes;

import financeira.gestao.demo.domain.transacao.TipoTransacao;
import financeira.gestao.demo.application.dto.request.TransacaoRequest;
import financeira.gestao.demo.application.dto.response.TransacaoResponse;

public interface OperacaoFinanceira {

    TipoTransacao getTipo();

    TransacaoResponse registrar(TransacaoRequest request, Long userId);

    String getCodigo();
}
