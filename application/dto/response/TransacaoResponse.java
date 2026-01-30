package financeira.gestao.demo.application.dto.response;

import financeira.gestao.demo.domain.entities.transacao.StatusTransacao;
import financeira.gestao.demo.domain.entities.transacao.TipoTransacao;
import financeira.gestao.demo.domain.entities.transacao.Transacao;

import java.math.BigDecimal;

public class TransacaoResponse {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private TipoTransacao tipo;
    private StatusTransacao status;
    private String moeda;
    private BigDecimal taxaCambio;

    public TransacaoResponse(Transacao transacao) {
        this.id = transacao.getId();
        this.descricao = transacao.getDescricao();
        this.valor = transacao.getValor();
        this.tipo = transacao.getTipo();
        this.status = transacao.getStatus();
        this.moeda = transacao.getMoeda();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getTaxaCambio() {
        return taxaCambio;
    }

    public void setTaxaCambio(BigDecimal taxaCambio) {
        this.taxaCambio = taxaCambio;
    }
}
