package financeira.gestao.demo.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaldoResponse {

    private Long id;
    private Long userId;
    private BigDecimal saldo;
    private BigDecimal limite;
    private LocalDateTime ultimaAtualizacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public BigDecimal getLimite() { return limite; }
    public void setLimite(BigDecimal limite) { this.limite = limite; }

    public LocalDateTime getUltimaAtualizacao() { return ultimaAtualizacao; }
    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }
}
