import conexao.JPAUtil;
import dao.ClienteDAO;
import dao.SolicitacaoCreditoDAO;
import modelo.entidades.Cliente;
import modelo.entidades.SolicitacaoCredito;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Optional;

public class SalvarSolicitacaoTeste2 {
    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        SolicitacaoCreditoDAO solicitacaoCreditoDAO = new SolicitacaoCreditoDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);

        Optional<Cliente> clienteOptional = clienteDAO.buscarPorId(15L);

        Cliente cliente = clienteOptional.get();
        SolicitacaoCredito solicitacaoCredito = new SolicitacaoCredito(
                cliente,
                new BigDecimal("20000.00"),
                new BigDecimal("650.00"),
                "APROVADO"
        );

        if(cliente != null){
            solicitacaoCreditoDAO.salvar(solicitacaoCredito);
            System.out.println(
                    "Solicitação feita com sucesso!"+
                            "\nStatus: "+solicitacaoCredito.getStatus()
            );
        } else {
            System.err.println("Falha com a solicitação...");
        }
    }
}
