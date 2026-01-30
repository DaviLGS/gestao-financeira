package financeira.gestao.demo.infra.service.transacao.config;

import financeira.gestao.demo.infra.service.transacao.operacoes.*;
import financeira.gestao.demo.infra.service.transacao.transacaoService.TransacaoService;
import financeira.gestao.demo.domain.repository.TransacaoRepository;
import financeira.gestao.demo.infra.service.cambio.CambioService;
import financeira.gestao.demo.infra.service.transacao.transacaoService.TransacaoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TransacaoConfig {

    @Bean
    public TransacaoService transacaoService(
            CompraService compra,
            SaqueService saque,
            DepositoService deposito,
            TransferenciaService transferencia,
            TransacaoRepository transacaoRepository,
            CambioService cambioService
    ) {
        List<OperacaoFinanceira> operacoes = List.of(
                compra,
                saque,
                deposito,
                transferencia
        );

        return new TransacaoServiceImpl(operacoes, transacaoRepository, cambioService);
    }
}
