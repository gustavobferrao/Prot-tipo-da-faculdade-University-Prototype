package modelo.entidades;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "solicitacoes_credito")
public class SolicitacaoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorSolicitado;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal juros;

    @Column(nullable = false)
    private String status; //APROVADO, NEGADO, ANALISE_MANUAL

    public SolicitacaoCredito(Cliente cliente,
                              BigDecimal valorSolicitado,
                              BigDecimal juros,
                              String status) {
        this.cliente = cliente;
        this.valorSolicitado = valorSolicitado;
        this.juros = juros;
        this.status = status;
    }

    public SolicitacaoCredito() {}

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
