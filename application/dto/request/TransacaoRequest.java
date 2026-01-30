package financeira.gestao.demo.application.dto.request;

import financeira.gestao.demo.domain.entities.transacao.StatusTransacao;
import financeira.gestao.demo.domain.entities.transacao.TipoTransacao;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class TransacaoRequest {

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 3, max = 255)
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    @NotBlank(message = "Moeda é obrigatória")
    @Pattern(regexp = "BRL|USD|EUR", message = "Moeda inválida")
    private String moeda;

    @NotNull(message = "Tipo é obrigatório")
    private TipoTransacao tipo;

    private StatusTransacao status;

    @NotBlank(message = "Código da operação é obrigatório")
    private String codigo;

    private boolean atualizarSaldo = true;

    public boolean isAtualizarSaldo() {
        return atualizarSaldo;
    }
    public Boolean getAtualizarSaldo() {
        return atualizarSaldo;
    }
    public void setAtualizarSaldo(boolean atualizarSaldo) {
        this.atualizarSaldo = atualizarSaldo;
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

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public void setStatus(StatusTransacao status) {
        this.status = status;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
