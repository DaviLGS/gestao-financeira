package financeira.gestao.demo.infra.kafka.consumer;

import financeira.gestao.demo.infra.kafka.event.TransacaoEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransacaoConsumer {

    @KafkaListener(
            topics = "transacoes-topic",
            groupId = "financeiro-group"
    )
    public void consumir(TransacaoEvent event) {

        System.out.println("Transação recebida do Kafka:");
        System.out.println("ID: " + event.getTransacaoId());
        System.out.println("Usuário: " + event.getUserId());
        System.out.println("Valor: " + event.getValor());
        System.out.println("Moeda: " + event.getMoeda());
        System.out.println("Tipo: " + event.getTipo());
    }
}
