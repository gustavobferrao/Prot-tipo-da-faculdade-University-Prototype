package service;

import modelo.entidades.Cliente;
import modelo.entidades.SolicitacaoCredito;

import java.math.BigDecimal;

public class MotorRegras {

    private static final BigDecimal LIMITE_RENDA_ALTA = new BigDecimal("3000.00");
    private static final BigDecimal LIMITE_RENDA_BAIXA = new BigDecimal("1500.00");
    private static final BigDecimal TAXA_JUROS = new BigDecimal("0.05");
    private static final BigDecimal TARIFA_FIXA = new BigDecimal("50.00");

    public SolicitacaoCredito avaliar(Cliente cliente, BigDecimal valorSolicitado){

        SolicitacaoCredito solicitacao = new SolicitacaoCredito();
        solicitacao.setCliente(cliente);
        solicitacao.setValorSolicitado(valorSolicitado);

        String statusFinal;
        BigDecimal jurosTotal = BigDecimal.ZERO;

        // REGRA CONDICIONAL AND: Renda > 3000 E Score > 700
        if (cliente.getRenda().compareTo(LIMITE_RENDA_ALTA) > 0 && cliente.getScore() > 700){
            statusFinal = "APROVADO";
            //PROCESSAMENTO JUROS: J = ax +b -> J = 0.05 * valor + 50
            jurosTotal= valorSolicitado.multiply(TAXA_JUROS).add(TARIFA_FIXA);
            //REGRA CONDICIONAL OR: Renda < 1500 OU Score < 500
        }
        else if (cliente.getRenda().compareTo(LIMITE_RENDA_BAIXA) < 0 || cliente.getScore() < 500) {
            statusFinal = "NEGADO";

            //OUTROS CASOS
        } else {
            statusFinal = "ANALISE MANUAL";
        }
        solicitacao.setStatus(statusFinal);
        solicitacao.setJuros(jurosTotal);
        return solicitacao;
    }
}
