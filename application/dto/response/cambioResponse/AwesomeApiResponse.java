package financeira.gestao.demo.application.dto.response.cambioResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class AwesomeApiResponse {

    @JsonProperty("USDBRL")
    private AwesomeApiPair USDBRL;

    @JsonProperty("EURBRL")
    private AwesomeApiPair EURBRL;

    public AwesomeApiPair getUSDBRL() {
        return USDBRL;
    }

    public void setUSDBRL(AwesomeApiPair USDBRL) {
        this.USDBRL = USDBRL;
    }

    public AwesomeApiPair getEURBRL() {
        return EURBRL;
    }

    public void setEURBRL(AwesomeApiPair EURBRL) {
        this.EURBRL = EURBRL;
    }

    public BigDecimal getBid(String moeda) {
        return switch (moeda.toUpperCase()) {
            case "USD" -> USDBRL != null ? USDBRL.getBid() : null;
            case "EUR" -> EURBRL != null ? EURBRL.getBid() : null;
            default -> null;
        };
    }
}

