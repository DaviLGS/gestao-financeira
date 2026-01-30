package financeira.gestao.demo.service.saldo;

import financeira.gestao.demo.application.dto.response.SaldoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaldoBancarioService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL =
            "https://697a84120e6ff62c3c599314.mockapi.io/saldos";
    public SaldoResponse buscarSaldo(Long userId) {

        SaldoResponse[] response = restTemplate.getForObject(
                BASE_URL + "?userId={userId}",
                SaldoResponse[].class,
                userId
        );

        if (response == null || response.length == 0) {
            SaldoResponse novoSaldo = new SaldoResponse();
            novoSaldo.setUserId(userId);
            novoSaldo.setSaldo(BigDecimal.valueOf(10000));
            novoSaldo.setLimite(BigDecimal.valueOf(20000));
            novoSaldo.setUltimaAtualizacao(LocalDateTime.now());

            SaldoResponse criado = restTemplate.postForObject(BASE_URL, novoSaldo, SaldoResponse.class);
            return criado;
        }
        return response[0];
    }

    public void atualizarSaldo(SaldoResponse saldo) {

        if (saldo.getId() == null) {
            throw new RuntimeException("ID do MockAPI não encontrado");
        }

        String url = BASE_URL + "/" + saldo.getId();

        Map<String, Object> body = new HashMap<>();
        body.put("userId", saldo.getUserId());
        body.put("saldo", saldo.getSaldo());
        body.put("limite", saldo.getLimite());
        body.put("ultimaAtualizacao", LocalDateTime.now().toString());

        restTemplate.put(url, body);
    }
}
