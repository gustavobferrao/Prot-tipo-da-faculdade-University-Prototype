import conexao.JPAUtil;
import dao.ClienteDAO;
import dao.SolicitacaoCreditoDAO;
import modelo.entidades.Cliente;
import modelo.entidades.SolicitacaoCredito;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Optional;

public class SalvarClienteTeste {
    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAO clienteDAO = new ClienteDAO(em);
        Cliente cliente = new Cliente(
                "Oliver Queen",
                "13636125373",
                "abcdef",
                new BigDecimal("50000.00"),
                950
        );

        try {
            if (cliente != null){
                clienteDAO.salvar(cliente);
                System.out.println("Cliente " + cliente.getNome() + " salvo com sucesso!");
            } else {
                System.err.println("Falha ao salvar cliente");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Executando o commit final do fluxo...");
// Certifique-se de que o commit do service ou da main está sendo chamado aqui!

// 🔎 ADICIONE ESTAS LINHAS LOGO APÓS O FLUXO FINANCEIRO TERMINAR:
        EntityManager emTeste = JPAUtil.getEntityManager();
        Long totalClientes = emTeste.createQuery("select count(c) from Cliente c", Long.class).getSingleResult();
        System.out.println(">>> [CONFIRMAÇÃO] Quantidade de clientes na tabela agora: " + totalClientes);
        emTeste.close();
        em.close();
    }
}
