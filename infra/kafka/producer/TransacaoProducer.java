package financeira.gestao.demo.infra.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import financeira.gestao.demo.infra.kafka.event.TransacaoEvent;
import financeira.gestao.demo.domain.transacao.Transacao;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransacaoProducer {

    private static final String TOPIC = "transacoes-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TransacaoProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarTransacao(Transacao transacao) {
        try {
            TransacaoEvent event = new TransacaoEvent(
                    transacao.getId(),
                    transacao.getUsuario().getId(),
                    transacao.getDescricao(),
                    transacao.getValor(),
                    transacao.getMoeda(),
                    transacao.getTipo().name(),
                    transacao.getCriadoAs()
            );

            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, transacao.getId().toString(), json);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar Kafka", e);
        }
    }
}
