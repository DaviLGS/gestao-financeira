package financeira.gestao.demo.application.dto.response.cambioResponse;

import java.math.BigDecimal;

public class AwesomeApiResponse {

    private AwesomeApiPair USDBRL;
    private AwesomeApiPair EURBRL;

    public AwesomeApiPair getUSDBRL() { return USDBRL; }
    public void setUSDBRL(AwesomeApiPair uSDBRL) { USDBRL = uSDBRL; }

    public AwesomeApiPair getEURBRL() { return EURBRL; }
    public void setEURBRL(AwesomeApiPair eURBRL) { EURBRL = eURBRL; }

    public BigDecimal getBid(String moeda) {
        switch (moeda.toUpperCase()) {
            case "USD": return USDBRL != null ? USDBRL.getBid() : null;
            case "EUR": return EURBRL != null ? EURBRL.getBid() : null;
            default: return null;
        }
    }
}

