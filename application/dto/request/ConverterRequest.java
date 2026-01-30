package financeira.gestao.demo.application.dto.request;

import java.math.BigDecimal;

public class ConverterRequest {

    private BigDecimal valor;
    private String moeda;

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getMoeda() { return moeda; }
    public void setMoeda(String moeda) { this.moeda = moeda; }

}
