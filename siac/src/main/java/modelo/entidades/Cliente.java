package modelo.entidades;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "clientes_siac")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_cliente", length = 55, nullable = false)
    private String nome;

    @Column(name = "cpf_cliente", length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(name ="senha_cliente", length = 25, nullable = false)
    private String senha;

    @Column(name = "renda_cliente", nullable = false, precision = 12, scale = 2)
    private BigDecimal renda;

    @Column(name = "score", nullable = false)
    private int score;

    public Cliente(String nome, String cpf, String senha, BigDecimal renda, int score) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.renda = renda;
        this.score = score;
    }

    public Cliente(){}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public BigDecimal getRenda() {
        return renda;
    }

    public void setRenda(BigDecimal renda) {
        this.renda = renda;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
