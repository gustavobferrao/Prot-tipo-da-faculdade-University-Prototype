package dao;

import javax.persistence.EntityManager;

public class DAO<E> {
    protected final EntityManager em;
    private Class<E> classe;

    // CONSTRUTOR: Recebe um EntityManager de fora (Essencial para o Service unificar transações)
    public DAO(Class<E> classe, EntityManager em){
        this.classe = classe;
        this.em = em;
    }

    public DAO<E> abrirT(){
        em.getTransaction().begin();
        return this;
    }
    public DAO<E> fecharT(){
        em.getTransaction().commit();
        return this;
    }
    public DAO<E> incluir(E entidade){
        em.persist(entidade);
        return this;
    }

}
