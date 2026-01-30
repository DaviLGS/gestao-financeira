package financeira.gestao.demo.application.usecase;

import financeira.gestao.demo.domain.entities.transacao.Transacao;
import financeira.gestao.demo.infra.kafka.producer.TransacaoProducer;
import financeira.gestao.demo.domain.repository.TransacaoRepository;
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
