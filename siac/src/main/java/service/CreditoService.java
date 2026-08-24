package service;

import dao.ClienteDAO;
import dao.SolicitacaoCreditoDAO;
import modelo.entidades.Cliente;
import modelo.entidades.SolicitacaoCredito;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Optional;

public class CreditoService {

    private final ClienteDAO clienteDAO;
    private final SolicitacaoCreditoDAO solicitacaoDAO;
    private final MotorRegras mr;
    private final EntityManager em;

    //O SERVICE RECEBE OS DAOs E O ENTITYMANAGER POR CONSTRUTOR
    public CreditoService(ClienteDAO clienteDAO, SolicitacaoCreditoDAO solicitacaoDAO,
                          EntityManager em, MotorRegras mr){
        this.clienteDAO = clienteDAO;
        this.solicitacaoDAO = solicitacaoDAO;
        this.mr = mr;
        this.em = em;
    }

    public SolicitacaoCredito processarFluxoCredito(String nome, String cpf,String senha, BigDecimal renda,
                                                    Integer score, BigDecimal valorSolicitado){

        try {
            //INICIA A TRANSAÇÃO NA CAMADA DE SERVIÇO
            em.getTransaction().begin();

            //ENTRADA DE DADOS: LOGIN/CADASTRO
            Optional<Cliente> clienteOptional = clienteDAO.buscarPorCpf(cpf);
            Cliente cliente;

            //VERIFICA: SE LOGIN OU CADASTRO
            if(clienteOptional.isPresent()){
                cliente = clienteOptional.get();
                System.out.println("Login efetuado para o cliente: "+cliente.getNome());
            } else {
                cliente = new Cliente();
                cliente.setCpf(cpf);
                cliente.setNome(nome);
                cliente.setSenha(senha);
                cliente.setRenda(renda);
                cliente.setScore(0);

                clienteDAO.salvar(cliente);
                System.out.println("Novo cadastro realizado para: "+cliente.getNome());
            }

            //MOTOR DE REGRAS: delega a solicitaçao para a classe especialista (MotorRegras)
            SolicitacaoCredito solicitacao = mr.avaliar(cliente, valorSolicitado);

            //GRAVA O RESULTADO RETORNADO PELO MOTOR
            solicitacaoDAO.salvar(solicitacao);

            em.getTransaction().commit();
            return solicitacao;
        } catch (Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }

    }
}
