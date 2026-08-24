package dao;

import conexao.JPAUtil;
import modelo.entidades.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class ClienteDAO extends DAO<Cliente> {

    //Construtor sorbecarregado para uso com a camada Service
    public ClienteDAO(EntityManager em){
        super(Cliente.class, em);
    }

    //SALVA O CLIENTE
    public void salvar(Cliente cliente){
      this.incluir(cliente);
    }

    //ATUALIZA DADOS DO CLIENTE
   public void atualizar(Cliente cliente){
        em.merge(cliente);
   }

   //REMOVE O CLIENTE, USANDO UM TERNARIO PARA GERENCIAR A REMOÇÃO
   public void deletar(Cliente cliente){
        em.remove(em.contains(cliente) ? cliente : em.merge(cliente));
   }

   //REALIZA BUSCA POR ID
   public Optional<Cliente> buscarPorId(Long id){
        Cliente cliente = em.find(Cliente.class, id);
        return Optional.ofNullable(cliente);
   }

   //REALIZA A BUSCA POR CPF ATRAVÉS DE UMA QUERY
   public Optional<Cliente> buscarPorCpf(String cpf){
        try {
            Cliente cliente =
                    em.createQuery
                            ("SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
            return Optional.of(cliente);
        } catch (NoResultException e){
            return Optional.empty();
        }
   }

   public Optional<Cliente> realizarLogin(String cpf, String senha){
        try {
            Cliente cliente = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.cpf = :cpf AND c.senha = :senha", Cliente.class)
                    .setParameter("cpf", cpf)
                    .setParameter("senha", senha)
                    .getSingleResult();
            return Optional.of(cliente);
        } catch (NoResultException e){
            System.err.println("Não foi possível encontrar o usuário: "+e.getMessage());
            return Optional.empty(); // CPF ou SENHA incorretos
        }
   }

   //FECHA
   public void fechar(){
        em.close();
   }


}
