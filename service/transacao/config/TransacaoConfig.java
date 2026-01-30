package financeira.gestao.demo.service.transacao.config;

import financeira.gestao.demo.repository.TransacaoRepository;
import financeira.gestao.demo.service.cambio.CambioService;
import financeira.gestao.demo.service.transacao.operacoes.*;
import financeira.gestao.demo.service.transacao.transacaoService.TransacaoService;
import financeira.gestao.demo.service.transacao.transacaoService.TransacaoServiceImpl;
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
