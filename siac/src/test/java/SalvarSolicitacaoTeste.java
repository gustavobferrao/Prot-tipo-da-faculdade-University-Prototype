import conexao.JPAUtil;
import dao.ClienteDAO;
import dao.SolicitacaoCreditoDAO;
import modelo.entidades.Cliente;
import modelo.entidades.SolicitacaoCredito;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class SalvarSolicitacaoTeste {
    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        Cliente cliente = new Cliente("Tony Stark",
                "233445566",
                "1234567",
                new BigDecimal("18000.00"),
                250);
        ClienteDAO clienteDao = new ClienteDAO(em);
        SolicitacaoCredito solicitacaoCredito = new SolicitacaoCredito(cliente, new BigDecimal("100000.00"), new BigDecimal("950.00"), "APROVADO");
        SolicitacaoCreditoDAO solicitacaoCreditoDAO = new SolicitacaoCreditoDAO(em);

        try {
            if (cliente != null) {
                clienteDao.salvar(cliente);
                System.out.println("Cliente "+cliente.getNome()+" salvo com sucesso!");

                solicitacaoCreditoDAO.salvar(solicitacaoCredito);
                System.out.println("Solicitação do cliente "+solicitacaoCredito.getCliente().getNome()+" salva com sucesso!");
            } else {
                System.err.println("Erro: não é possivel salvar um cliente nulo");
            }
        } catch (Exception e){
            System.err.println("Falha ao salvar o cliente no banco de dados: "+e.getMessage());
        }
    }
}
