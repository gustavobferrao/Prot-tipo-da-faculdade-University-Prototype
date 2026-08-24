import conexao.JPAUtil;
import dao.ClienteDAO;
import modelo.entidades.Cliente;

import javax.persistence.EntityManager;
import java.util.Optional;

public class BuscarClientePorCpfTeste {
    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAO clienteDAO = new ClienteDAO(em);

        Optional<Cliente> clienteOptional = clienteDAO.buscarPorId(5L);
        Cliente cliente = clienteOptional.get();

        try {
            if(cliente != null){
                clienteDAO.buscarPorCpf(cliente.getCpf());
                System.out.println("=== CLIENTE ENCONTRADO ===");
                System.out.println("NOME: "+cliente.getNome());
                System.out.println("CPF: "+cliente.getCpf());
                System.out.println("RENDA: "+cliente.getRenda());
                System.out.println("SCORE: "+cliente.getScore());
                System.out.println("=== CLIENTE ENCONTRADO ===");
            } else {
                System.out.println("Falha em encontrar cliente!");
            }
        } catch (Exception e){
            System.err.println("Erro ao encontrar cliente: "+e.getMessage());
            throw e;
        }
    }
}
