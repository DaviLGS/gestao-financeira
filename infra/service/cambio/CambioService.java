package financeira.gestao.demo.infra.service.cambio;

import financeira.gestao.demo.application.dto.response.cambioResponse.AwesomeApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CambioService {

    private final RestTemplate restTemplate;

    public CambioService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal converterParaBRL(BigDecimal valor, String moeda) {

        if (moeda.equalsIgnoreCase("BRL")) {
            return valor;
        }

        String url = "https://economia.awesomeapi.com.br/json/last/" + moeda.toUpperCase() + "-BRL";

        AwesomeApiResponse apiResponse = restTemplate.getForObject(url, AwesomeApiResponse.class);

        if (apiResponse == null || apiResponse.getBid(moeda) == null) {
            throw new RuntimeException("Erro ao consultar câmbio externo");
        }

        BigDecimal taxa = apiResponse.getBid(moeda);

        return valor.multiply(taxa).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getTaxaCambio(String moeda) {

        if (moeda.equalsIgnoreCase("BRL")) {
            return BigDecimal.ONE;
        }

        String url = "https://economia.awesomeapi.com.br/json/last/" + moeda + "-BRL";

        AwesomeApiResponse apiResponse =
                restTemplate.getForObject(url, AwesomeApiResponse.class);

        if (apiResponse == null || apiResponse.getBid(moeda) == null) {
            throw new RuntimeException("Erro ao consultar câmbio externo");
        }

        return apiResponse.getBid(moeda);
    }
}
