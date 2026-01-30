package financeira.gestao.demo.infra.kafka.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoEvent {

    private Long transacaoId;
    private Long userId;
    private String descricao;
    private BigDecimal valor;
    private String moeda;
    private String tipo;
    private LocalDateTime criadoEm;

    public TransacaoEvent() {}

    public TransacaoEvent(Long transacaoId, Long userId, String descricao, BigDecimal valor,
                          String moeda, String tipo, LocalDateTime criadoEm) {
        this.transacaoId = transacaoId;
        this.userId = userId;
        this.descricao = descricao;
        this.valor = valor;
        this.moeda = moeda;
        this.tipo = tipo;
        this.criadoEm = criadoEm;
    }

    public Long getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(Long transacaoId) {
        this.transacaoId = transacaoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
