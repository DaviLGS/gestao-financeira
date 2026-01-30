package financeira.gestao.demo.controller.kafka;

import financeira.gestao.demo.domain.transacao.Transacao;
import financeira.gestao.demo.infra.kafka.producer.TransacaoProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final TransacaoProducer producer;

    public KafkaController(TransacaoProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/teste")
    public void testarKafka() {
        Transacao t = new Transacao();
        t.setId(1L);
        t.setDescricao("Teste Kafka");
        t.setValor(BigDecimal.TEN);
        t.setMoeda("BRL");
        t.setCriadoAs(LocalDateTime.now());

        producer.enviarTransacao(t);
    }
}
