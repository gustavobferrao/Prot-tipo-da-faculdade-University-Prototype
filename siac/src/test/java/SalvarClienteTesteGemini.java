import conexao.JPAUtil;
import dao.ClienteDAO;
import modelo.entidades.Cliente;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class SalvarClienteTesteGemini {
    public static void main(String[] args) {

        // 1. ESTABELECE A CONEXÃO: Abre o gerenciador único usando a nossa infraestrutura
        EntityManager em = JPAUtil.getEntityManager();

        // 2. INJEÇÃO DE DEPENDÊNCIA: Cria o DAO passando a conexão ativa
        ClienteDAO clienteDao = new ClienteDAO(em);

        try {
            System.out.println("=== INICIANDO TESTE DE CADASTRO ===");

            // 3. ABRE A TRANSAÇÃO: Obrigatório para operações de escrita (INSERT)
            em.getTransaction().begin();

            // 4. CRIA O OBJETO: Preenchendo todos os campos obrigatórios (inclusive a senha!)
            Cliente novoCliente = new Cliente();
            novoCliente.setCpf("98765432101");
            novoCliente.setNome("João Pedro");
            novoCliente.setRenda(new BigDecimal("1700.00"));
            novoCliente.setScore(350);
            novoCliente.setSenha("metropolis2026"); // O campo que estava faltando!

            // 5. ENVIA PARA O BANCO: O DAO anexa o objeto ao contexto do Hibernate
            System.out.println("Enviando dados do cliente para o DAO...");
            clienteDao.salvar(novoCliente);

            // 6. CONFIRMA OS DADOS: Salva fisicamente na tabela clientes_siac
            System.out.println("Executando o commit na transação...");
            em.getTransaction().commit();

            System.out.println("🎉 SUCESSO: Cliente '" + novoCliente.getNome() + "' gravado com sucesso!");

            // 7. PROVA DOS NOVES: Faz uma consulta rápida para provar que ele está lá
            Long totalNoBanco = em.createQuery("select count(c) from Cliente c", Long.class).getSingleResult();
            System.out.println(">>> Confirmação real no banco de dados. Total de registros: " + totalNoBanco);

        } catch (Exception e) {
            // Se qualquer coisa falhar (como CPF duplicado), desfaz a operação para não quebrar o banco
            if (em != null && em.getTransaction().isActive()) {
                System.err.println("💥 Ocorreu um erro! Executando Rollback...");
                em.getTransaction().rollback();
            }
            System.err.println("Falha ao rodar o teste de cadastro. Motivo:");
            e.printStackTrace();
        } finally {
            // 8. FECHAMENTO OBRIGATÓRIO: Libera a conexão com o banco
            if (em != null && em.isOpen()) {
                em.close();
            }
            JPAUtil.close();
            System.out.println("Conexões encerradas.");
        }
    }
}
