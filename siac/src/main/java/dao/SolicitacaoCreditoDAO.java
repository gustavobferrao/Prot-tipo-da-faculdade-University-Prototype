package dao;

import modelo.entidades.SolicitacaoCredito;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class SolicitacaoCreditoDAO extends DAO<SolicitacaoCredito>{

    //Construtor sorbecarregado para uso com a camada Service
    public SolicitacaoCreditoDAO(EntityManager em){
        super(SolicitacaoCredito.class, em);
    }

    //SALVA A SOLICITACAO
    public void salvar(SolicitacaoCredito entidade){
         this.incluir(entidade);
    }

    public void deletarSolicitacao(SolicitacaoCredito solicitacaoCredito){
        em.remove(em.contains(solicitacaoCredito) ? solicitacaoCredito : em.merge(solicitacaoCredito));
    }

    public Optional<SolicitacaoCredito> buscarPorClienteId(Long id){
        try {
            SolicitacaoCredito solicitacaoCredito =
                    em.createQuery("SELECT s FROM SolicitacaoCredito s WHERE s.cliente_id = :id", SolicitacaoCredito.class)
                            .setParameter("id", id)
                            .getSingleResult();
            return Optional.of(solicitacaoCredito);
        } catch (NoResultException e){
            return Optional.empty();
        }
    }

    public void fechar(){
        em.close();
    }
}
