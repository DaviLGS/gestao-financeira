package financeira.gestao.demo.application.usecase;

import financeira.gestao.demo.application.dto.request.TransacaoRequest;
import financeira.gestao.demo.domain.transacao.Transacao;
import financeira.gestao.demo.infra.kafka.producer.TransacaoProducer;
import financeira.gestao.demo.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class CriarTransacaoUseCase {

    private final TransacaoRepository repository;
    private final TransacaoProducer producer;

    public CriarTransacaoUseCase(TransacaoRepository repository, TransacaoProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public Transacao executar(Transacao transacao) {

        Transacao salva = repository.save(transacao);

        producer.enviarTransacao(salva);

        return salva;
    }
}
