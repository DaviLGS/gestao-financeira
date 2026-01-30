package financeira.gestao.demo.application.dto.response.cambioResponse;

import java.math.BigDecimal;

public class CambioResponse {

    private String moeda;
    private BigDecimal rate;

    public String getCurrency() {
        return moeda;
    }

    public void setCurrency(String moeda) {
        this.moeda = moeda;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
